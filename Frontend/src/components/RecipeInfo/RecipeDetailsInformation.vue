<template>
    <div v-if="recipe" class="bg-white rounded-lg shadow-lg overflow-hidden">
        <RecipeHeader :recipe="recipe" :isEditing="isEditing" :edited-name="editedRecipe?.name ?? ''"
            @update:editedName="editedRecipe.name = $event" @startEditing="startEditing" @cancelEdit="cancelEdit"
            @saveEdit="saveEdit" @handleDelete="handleDelete" />

        <div>
            <template v-if="!isEditing">
                <RecipeIngredientsList :ingredients="recipe.ingredients" :instructions="recipe.instructions" />
            </template>
            <template v-else>
                <RecipeIngredientsEditor :recipe="editedRecipe" :isEditing="isEditing"
                    @update:recipe="updateEditedRecipe($event)" />
            </template>
        </div>
    </div>
    <div v-else class="text-center text-2xl font-semibold mt-8">Loading...</div>

</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { useCookies } from '@/composables/useCookies'
import { useRouter } from 'vue-router'

const router = useRouter()
const recipe = ref(null)
const isEditing = ref(false)
const editedRecipe = ref(null)
const props = defineProps(['recipeId'])

const updateEditedRecipe = (newRecipe) => {
    editedRecipe.value = newRecipe
}

const startEditing = () => {
    editedRecipe.value = JSON.parse(JSON.stringify(recipe.value))
    isEditing.value = true
}

const cancelEdit = () => {
    editedRecipe.value = null
    isEditing.value = false
}

const saveEdit = async () => {
    try {
        const response = await axios.put(`/recipes/${props.recipeId}`, editedRecipe.value, {
            headers: {
                Authorization: `Bearer ${useCookies().getCookie('jwt')}`,
            }
        })
        recipe.value = response.data
        isEditing.value = false
        router.go() // refresh the page
    } catch (error) {
        console.error('Error updating recipe:', error)
    }
}
const handleDelete = async () => {
    if (confirm('Are you sure you want to delete this recipe?')) {
        try {
            await axios.delete(`/recipes/${props.recipeId}`, {
                headers: {
                    Authorization: `Bearer ${useCookies().getCookie('jwt')}`,
                }
            })
            router.push('/recipes')
        } catch (error) {
            console.error('Error deleting recipe:', error)
        }
    }

}

const fetchRecipe = async () => {
    try {
        const response = await axios.get(`/recipes/${props.recipeId}`, {
            headers: {
                Authorization: `Bearer ${useCookies().getCookie('jwt')}`,
            }
        })
        recipe.value = response.data
    } catch (error) {
        console.error('Error fetching recipe:', error)
    }
}

onMounted(() => {
    fetchRecipe()
})

import { onBeforeRouteLeave } from 'vue-router'
import RecipeHeader from './RecipeHeader.vue'
import RecipeIngredientsList from './RecipeIngredientsList.vue'
import RecipeIngredientsEditor from './RecipeIngredientsEditor.vue'

// Prevent leaving the page if there are unsaved changes
onBeforeRouteLeave((to, from) => {
    if (!isEditing.value) {
        return true
    }
    const answer = confirm('You have unsaved changes. Are you sure you want to leave?')

    if (!answer) {
        return false
    } else {
        isEditing.value = false
        return false
    }
})
</script>
