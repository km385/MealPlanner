<template>
  <div v-if="isEditing" class="p-6">
    <div class="mb-8">
      <h2 class="text-2xl font-semibold mb-4">Ingredients</h2>
      <!-- Existing Ingredients -->
      <ul class="space-y-3 mb-6">
        <li
          v-for="(item, index) in editedRecipe.ingredients"
          :key="item.ingredient.id"
          class="flex justify-between items-center bg-gray-50 p-3 rounded"
        >
          <span class="font-medium">{{ item.ingredient.name }}</span>
          <div class="flex items-center gap-4">
            <input
              type="number"
              v-model.number="item.quantity"
              class="w-24 px-2 py-1 border rounded"
              min="0"
            />
            <span>{{ item.ingredient.unitOfMeasure }}</span>
            <button @click="removeIngredient(index)" class="text-red-600 hover:text-red-800 p-2">
              ×
            </button>
          </div>
        </li>
      </ul>

      <div class="flex items-end gap-4 bg-gray-50 p-4 rounded">
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

  <div class="mb-8">
    <h2 class="text-2xl font-semibold mb-4">Instructions</h2>
    <textarea
      v-model="editedRecipe.instructions"
      class="w-full p-3 border rounded"
      rows="4"
    ></textarea>
  </div>
</template>

<script setup>
import { ref, onUnmounted, watch, onMounted } from 'vue'

const handleKeydown = (e) => {
  // Don't trigger if user is typing in an input field or textarea
  if (e.target.tagName === 'INPUT' || e.target.tagName === 'TEXTAREA') {
    return
  }

  if (e.key === 'Enter') {
    console.log('Enter key pressed')
  }
}

onMounted(() => {
  if (props.isEditing) {
    window.addEventListener('keydown', handleKeydown)
  }
})

onUnmounted(() => {
  window.removeEventListener('keydown', handleKeydown)
})

const props = defineProps({
  recipe: {
    type: Object,
    required: true,
  },
  isEditing: {
    type: Boolean,
    default: false,
  },
})

const emit = defineEmits(['update:recipe'])

const editedRecipe = ref({ ...props.recipe })
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

watch(
  editedRecipe,
  (newValue) => {
    emit('update:recipe', newValue)
  },
  { deep: true },
)

const addIngredient = () => {
  editedRecipe.value.ingredients.push({
    ingredient: {
      name: newIngredientForm.value.name,
      unitOfMeasure: newIngredientForm.value.unitOfMeasure,
    },
    quantity: newIngredientForm.value.quantity,
  })
  newIngredientForm.value = { name: '', quantity: null, unitOfMeasure: units[0] }
}

const removeIngredient = (index) => {
  editedRecipe.value.ingredients.splice(index, 1)
}
</script>
