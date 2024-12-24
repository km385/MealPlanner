<template>
    <div class="h-[calc(100vh-theme(height.navbar))] flex items-center justify-center bg-gray-100">
        <div class="bg-white p-8 rounded-lg shadow-lg w-full max-w-md">
            <h2 class="text-2xl font-bold mb-6 text-center">Login</h2>
            <form @submit.prevent="handleLogin">
                <div class="mb-4">
                    <label for="email" class="block text-gray-700">Email</label>
                    <input
                        type="email"
                        id="email"
                        v-model="email"
                        class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring focus:border-blue-300"
                        required
                    />
                    <span v-if="formErrors.email" class="error">{{ formErrors.email }}</span>
                </div>
                <div class="mb-6">
                    <label for="password" class="block text-gray-700">Password</label>
                    <input
                        type="password"
                        id="password"
                        v-model="password"
                        class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring focus:border-blue-300"
                        required
                    />
                    <span v-if="formErrors.password" class="error">{{ formErrors.password }}</span>
                </div>
                <button
                    type="submit"
                    class="w-full bg-blue-500 text-white py-2 rounded-lg hover:bg-blue-600 transition duration-300"
                >
                    Login
                </button>

                <div v-if="errorMessage" class="text-red-500 mt-4 text-center">
                    {{ errorMessage }}
                </div>
            </form>
        </div>
    </div>
</template>


<script setup>
import { ref } from 'vue';
import axios from 'axios';
import { useAuthStore } from '@/stores/auth'
import { useRouter } from 'vue-router'

const router = useRouter()
const auth = useAuthStore()

const email = ref('test1@test.com');
const password = ref('password');
const errorMessage = ref('');
const formErrors = ref({});


const handleLogin = async () => {
    formErrors.value = {};
    errorMessage.value = '';
    try {
        const response = await axios.post('/auth/login', {
            email: email.value,
            password: password.value
        });
        const jwt = response.data.token;
        const expiresIn = response.data.expiresIn;
        auth.login(jwt, expiresIn);
        router.push('/');
    } catch (error) {
        if (error.response && error.response.status === 403) {
            errorMessage.value = 'Invalid email or password.';
        } else if (error.response && error.response.status === 400) {
            const errors = error.response.data;
            formErrors.value = errors;
        } else {
            console.error(error);
        }

    }
};

</script>
