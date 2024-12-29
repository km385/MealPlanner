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
            <p class="text-red-500" v-if="errorMessages">{{ errorMessages.details.name }}</p>
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
        :errors="errorMessages?.details"
      />
      <p v-if="errorMessages" class="px-6 text-red-500">{{ errorMessages.details.ingredients }}</p>
    </div>
    <!-- instructions -->
    <template v-if="!readonly">
      <div class="mb-8 p-6">
        <h2 class="text-2xl font-semibold mb-4">Instructions</h2>
        <textarea v-model="recipe.instructions" class="w-full p-3 border rounded" rows="4">
        </textarea>
        <p v-if="errorMessages" class="text-red-500">{{ errorMessages.details.instructions }}</p>
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
  errorMessages: {
    type: Object,
    required: false,
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
}

const handleKeydown = (e) => {
  // Don't trigger if user is typing in an input field or textarea
  if (e.target.tagName === 'INPUT' || e.target.tagName === 'TEXTAREA') {
    return
  }

  if (e.key === 'Enter') {
    saveEdit()
  }
}

onMounted(() => {
  window.addEventListener('keydown', handleKeydown)
})

onUnmounted(() => {
  window.removeEventListener('keydown', handleKeydown)
})

import { onBeforeRouteLeave } from 'vue-router'
onBeforeRouteLeave(() => {
  // change the value of readonly in parent to true to leave this form
  if (props.readonly) {
    return true
  }
  const answer = confirm('You have unsaved changes. Are you sure you want to leave?')

  if (!answer) {
    return false
  } else {
    emit('cancel:edit')
    return false
  }
})
</script>
