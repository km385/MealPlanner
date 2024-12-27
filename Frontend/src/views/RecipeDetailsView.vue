<script setup>
import { useRouter, useRoute } from 'vue-router'
import RecipeDetailsInformation from '@/components/RecipeInfo/RecipeDetailsInformation.vue'
const router = useRouter()
const route = useRoute()
</script>

<template>
  <div class="container mx-auto px-4 py-8">
    <button
      @click="router.back()"
      class="mb-4 px-4 py-2 bg-gray-800 text-white rounded hover:bg-gray-700"
    >
      Back to Recipes
    </button>
    <RecipeDetailsInformation :recipeId="route.params.id" />
  </div>
</template>
