<template>
  <div class="p-6">
    <div class="mb-8">
      <h2 class="text-2xl font-semibold mb-4">Ingredients</h2>
      <!-- Existing Ingredients -->
      <ul class="space-y-3 mb-6">
        <li
          v-for="(item, index) in ingredients"
          :key="item.ingredient.id"
          class="flex justify-between items-center bg-gray-50 p-3 rounded"
        >
          <span class="font-medium">{{ item.ingredient.name }}</span>
          <div class="flex items-center gap-4">
            <template v-if="!readonly">
              <input
                type="number"
                v-model.number="item.quantity"
                class="w-24 px-2 py-1 border rounded"
                min="0"
              />
            </template>
            <template v-else>
              <p>{{ item.quantity }}</p>
            </template>

            <span>{{ item.ingredient.unitOfMeasure }}</span>
            <button
              v-if="!readonly"
              @click="removeIngredient(index)"
              class="text-red-600 hover:text-red-800 p-2"
            >
              ×
            </button>
          </div>
        </li>
      </ul>
      <div v-if="!readonly" class="flex items-end gap-4 bg-gray-50 p-4 rounded">
        <div class="flex-1">
          <label class="block text-sm font-medium text-gray-700 mb-1"> Ingredient Name </label>
          <input
            type="text"
            v-model="newIngredientForm.name"
            class="w-full p-2 border rounded"
            placeholder="Enter ingredient name"
          />
        </div>
        <div class="w-32">
          <label class="block text-sm font-medium text-gray-700 mb-1"> Quantity </label>
          <input
            type="number"
            v-model.number="newIngredientForm.quantity"
            class="w-full p-2 border rounded"
            min="0"
          />
        </div>
        <div class="w-40">
          <label class="block text-sm font-medium text-gray-700 mb-1"> Unit </label>
          <select v-model="newIngredientForm.unitOfMeasure" class="w-full p-2 border rounded">
            <option v-for="unit in units" :key="unit" :value="unit">
              {{ unit }}
            </option>
          </select>
        </div>
        <button
          @click="addIngredient"
          :disabled="!newIngredientForm.name || !newIngredientForm.quantity"
          class="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 disabled:bg-gray-400"
        >
          Add
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'

const props = defineProps({
  initialIngredients: {
    type: Array,
    default: () => [],
    required: true,
  },
  readonly: {
    type: Boolean,
    default: false,
  },
})
const ingredients = ref(props.initialIngredients)

const emit = defineEmits(['update:ingredients'])

const units = [
  'grams',
  'kilograms',
  'milliliters',
  'liters',
  'pieces',
  'tablespoons',
  'teaspoons',
  'cups',
]

const newIngredientForm = ref({
  name: '',
  quantity: null,
  unitOfMeasure: units[0],
})

// watch for changes in quantity in the ingredients array
watch(
  ingredients,
  (newValue) => {
    emit('update:ingredients', newValue)
  },
  { deep: true },
)

const addIngredient = () => {
  ingredients.value.push({
    ingredient: {
      name: newIngredientForm.value.name,
      unitOfMeasure: newIngredientForm.value.unitOfMeasure,
    },
    quantity: newIngredientForm.value.quantity,
  })
  newIngredientForm.value = { name: '', quantity: null, unitOfMeasure: units[0] }
  emit('update:ingredients', ingredients.value)
}

const removeIngredient = (index) => {
  ingredients.value.splice(index, 1)
  emit('update:ingredients', ingredients.value)
}
</script>
