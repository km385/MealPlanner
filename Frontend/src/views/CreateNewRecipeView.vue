<template>
  <div class="container mx-auto px-4 py-8">
    <TheForm :recipe="recipe" :readonly="false" @save:recipe="saveEdit" @cancel:edit="cancelEdit" />
  </div>
</template>

<script setup>
import { onBeforeRouteLeave } from 'vue-router'
import { ref } from 'vue'
import axios from 'axios'
import { useCookies } from '@/composables/useCookies'
import { useRouter } from 'vue-router'
import TheForm from './TheForm.vue'

const router = useRouter()
const recipe = ref({
  name: '',
  ingredients: [],
  instructions: '',
})

const saveEdit = async () => {
  try {
    console.log(recipe.value)
    await axios.post(`/recipes`, recipe.value, {
      headers: {
        Authorization: `Bearer ${useCookies().getCookie('jwt')}`,
      },
    })
    router.push('/recipes')
  } catch (error) {
    console.error('Error updating recipe:', error)
  }
}

const cancelEdit = () => {
  router.push('/recipes')
}

// Prevent leaving the page if there are unsaved changes
// onBeforeRouteLeave(() => {
//   const answer = confirm('You have unsaved changes. Are you sure you want to leave?')

//   if (!answer) {
//     return false
//   } else {
//     return true
//   }
// })
</script>
