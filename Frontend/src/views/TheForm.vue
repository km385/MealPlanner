<template>
  <div class="bg-white rounded-lg shadow-lg overflow-hidden">
    <!-- header -->
    <div class="bg-gray-800 text-white p-6">
      <div class="flex justify-between items-center">
        <div class="">
          <template v-if="readonly">
            <h1 class="text-3xl font-bold">{{ recipe.name }}</h1>
          </template>
          <template v-else>
            <input
              v-model="recipe.name"
              class="w-full px-3 py-2 text-gray-800 rounded"
              placeholder="Recipe name"
            />
          </template>
        </div>

        <div class="flex gap-2">
          <button
            v-if="readonly"
            @click="startEditing"
            class="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 transition-colors duration-200"
          >
            Edit
          </button>
          <button
            v-if="readonly"
            @click="deleteRecipe"
            class="px-4 py-2 bg-red-600 text-white rounded hover:bg-red-700 transition-colors duration-200"
          >
            Delete
          </button>
          <button
            v-if="!readonly"
            @click="saveEdit"
            class="px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700 transition-colors duration-200"
          >
            Save
          </button>
          <button
            v-if="!readonly"
            @click="cancelEdit"
            class="px-4 py-2 bg-gray-600 text-white rounded hover:bg-gray-700 transition-colors duration-200"
          >
            Cancel
          </button>
        </div>
      </div>
    </div>
    <!-- ingredients -->
    <div>
      <RecipeIngredientsEditorCreate
        :initial-ingredients="recipe.ingredients"
        @update:ingredients="updateIngredients"
        :readonly="readonly"
      />
    </div>
    <!-- instructions -->
    <template v-if="!readonly">
      <div class="mb-8 p-6">
        <h2 class="text-2xl font-semibold mb-4">Instructions</h2>
        <textarea v-model="recipe.instructions" class="w-full p-3 border rounded" rows="4">
        </textarea>
      </div>
    </template>
    <template v-else>
      <div class="mb-8 p-6">
        <h2 class="text-2xl font-semibold mb-4">Instructions</h2>
        <p class="text-gray-600 whitespace-pre-line">{{ recipe.instructions }}</p>
      </div>
    </template>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import RecipeIngredientsEditorCreate from '@/components/RecipeInfo/RecipeIngredientsEditor - create.vue'
const props = defineProps({
  recipe: {
    type: Object,
    required: true,
  },
  readonly: {
    type: Boolean,
    required: true,
  },
})

const emit = defineEmits(['save:recipe', 'edit:recipe', 'cancel:edit', 'delete:recipe'])

import { onMounted, onUnmounted } from 'vue'
import { watch } from 'vue'
const recipe = ref(props.recipe)

watch(
  () => props.recipe,
  (newValue) => {
    recipe.value = newValue
  },
  { deep: true },
)

const startEditing = () => {
  console.log('start editing')
  emit('edit:recipe')
}

const cancelEdit = () => {
  emit('cancel:edit')
}

const saveEdit = () => {
  emit('save:recipe', recipe.value)
}

const deleteRecipe = () => {
  emit('delete:recipe')
}

const updateIngredients = (newIngredients) => {
  recipe.value.ingredients = newIngredients
  console.log(recipe.value)
}

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
  console.log(recipe.value)
  window.addEventListener('keydown', handleKeydown)
})

onUnmounted(() => {
  window.removeEventListener('keydown', handleKeydown)
})

import { onBeforeRouteLeave } from 'vue-router'
// onBeforeRouteLeave(() => {
//   console.log('readonly:', readonly)
//   if (readonly.value) {
//     return true
//   }
//   const answer = confirm('You have unsaved changes. Are you sure you want to leave?')

//   if (!answer) {
//     return false
//   } else {
//     emit('update:readonly', true)
//     return false
//   }
// })
</script>
