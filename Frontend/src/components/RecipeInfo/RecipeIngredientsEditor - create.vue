<template>
  <div class="p-6">
    <div class="mb-8">
      <h2 class="text-2xl font-semibold mb-4">Ingredients</h2>
      <!-- Existing Ingredients -->
      <ul class="space-y-3 mb-6">
        <li
          v-for="(item, index) in ingredients"
          :key="item.ingredient.id"
          class="flex flex-col bg-gray-50 p-3 rounded"
        >
          <div class="flex justify-between items-start w-full">
            <div class="flex flex-col">
              <span class="font-medium max-w-[300px] truncate inline-block">{{
                item.ingredient.name
              }}</span>
              <span
                v-if="getIngredientError(index, 'ingredient.name')"
                class="text-sm text-red-500 mt-1"
              >
                {{ getIngredientError(index, 'ingredient.name') }}
              </span>
            </div>

            <div class="flex items-start gap-4">
              <div class="flex flex-col">
                <template v-if="!readonly">
                  <input
                    type="number"
                    v-model.number="item.quantity"
                    class="w-24 px-2 py-1 border rounded"
                    :class="{ 'border-red-500': getIngredientError(index, 'quantity') }"
                    min="0"
                  />
                  <span
                    v-if="getIngredientError(index, 'quantity')"
                    class="text-sm text-red-500 mt-1"
                  >
                    {{ getIngredientError(index, 'quantity') }}
                  </span>
                </template>
                <template v-else>
                  <p>{{ item.quantity }}</p>
                </template>
              </div>

              <div class="flex flex-col">
                <span>{{ item.ingredient.unitOfMeasure }}</span>
                <span
                  v-if="getIngredientError(index, 'ingredient.unitOfMeasure')"
                  class="text-sm text-red-500 mt-1"
                >
                  {{ getIngredientError(index, 'ingredient.unitOfMeasure') }}
                </span>
              </div>

              <button
                v-if="!readonly"
                @click="removeIngredient(index)"
                class="text-red-600 hover:text-red-800 p-2"
              >
                ×
              </button>
            </div>
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
  errors: {
    type: Object,
    default: () => ({}),
  },
})

const getIngredientError = (index, field) => {
  return props.errors[`ingredients[${index}].${field}`]
}

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
