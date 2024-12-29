<template>
  <div class="container mx-auto px-4 py-8">
    <TheForm
      :recipe="recipe"
      :readonly="isReadOnly"
      :error-messages="errorMessages"
      @save:recipe="saveEdit"
      @cancel:edit="cancelEdit"
    />
  </div>
</template>

<script setup>
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

const isReadOnly = ref(false)
const errorMessages = ref(null)

const saveEdit = async () => {
  try {
    await axios.post(`/recipes`, recipe.value, {
      headers: {
        Authorization: `Bearer ${useCookies().getCookie('jwt')}`,
      },
    })
    isReadOnly.value = true
    router.push('/recipes')
  } catch (error) {
    errorMessages.value = error.response.data
    console.log(errorMessages.value)
  }
}

const cancelEdit = () => {
  isReadOnly.value = true
  router.push('/recipes')
}
</script>
