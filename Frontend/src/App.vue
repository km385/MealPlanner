<script setup>
import { RouterLink, RouterView, useRouter } from 'vue-router'
import HelloWorld from './components/HelloWorld.vue'
import axios from 'axios';
import { onMounted } from 'vue';

let yourJWTToken = "yJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MUB0ZXN0LmNvbSIsImlhdCI6MTczNDk1OTYzMCwiZXhwIjoxNzM1MDQ2MDMwfQ.d-WlvVeK7__aQlGwwWdwWjBAIS2DjHyuPta0Txm4hRU"


onMounted(async () => {
  // const data = await axios.post('/auth/login', {
  //   "email": "test1@test.com",
  //   "password": "password"
  // }
  // )
  // console.log(data.data)
  // yourJWTToken = data.data.token
  // const recipes = await axios
  //   .get('/recipes', {
  //     headers: {
  //       Authorization: "Bearer " + yourJWTToken
  //     }
  //   }
  //   )
  // console.log(recipes.data)

  
  
    
})

</script>

<template>
  <header>

    <div class="wrapper">
      <!-- <HelloWorld msg="You did it!" /> -->

      <nav class="flex items-center my-2">
        <RouterLink class="bg-red-400 p-2 rounded-md mr-2" to="/">Home</RouterLink>
        <RouterLink class="bg-red-400 p-2 rounded-md mr-2" to="/about">About</RouterLink>
        <RouterLink class="bg-red-400 p-2 rounded-md mr-2" to="/login">Login</RouterLink>
        <RouterLink class="bg-red-400 p-2 rounded-md mr-2" to="/register">Register</RouterLink>

      </nav>
      
    </div>
  </header>

  <RouterView />
</template>

