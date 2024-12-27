<template>
  <div class="p-6">
    <div class="mb-8">
      <h2 class="text-2xl font-semibold mb-4">Ingredients</h2>
      <ul class="space-y-3">
        <li
          v-for="item in ingredients"
          :key="item.ingredient.id"
          class="flex justify-between items-center bg-gray-50 p-3 rounded"
        >
          <span class="font-medium">{{ item.ingredient.name }}</span>
          <span class="text-gray-600">
            {{ item.quantity }} {{ item.ingredient.unitOfMeasure }}
          </span>
        </li>
      </ul>
    </div>

    <div>
      <h2 class="text-2xl font-semibold mb-4">Instructions</h2>
      <p class="text-gray-600 whitespace-pre-line">{{ instructions }}</p>
    </div>
  </div>
</template>

<script setup>
const props = defineProps(['ingredients', 'instructions'])
</script>
