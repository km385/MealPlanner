// import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import './index.css'
import VueCookies from 'vue-cookies'
import { createPinia } from 'pinia'

const app = createApp(App)

const pinia = createPinia()
app.use(pinia)

app.use(VueCookies, {
  expires: '1d',
  path: '/', // availability of cookies in the whole application
  domain: '', // current domain only can access the cookies
  secure: true, // only sent over https
  sameSite: 'Strict', // only same-site requests
})
app.use(router)

axios.defaults.baseURL = 'http://localhost:8080'
app.config.globalProperties.$axios = axios

app.mount('#app')
