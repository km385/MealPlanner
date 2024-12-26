<template>
    <div class="bg-gray-800 text-white p-6">
            <div class="flex justify-between items-center">
                <div v-if="!isEditing">
                    <h1 class="text-3xl font-bold">{{ recipe.name }}</h1>
                    <p class="text-gray-300 mt-2">Created by {{ recipe.userName }}</p>
                </div>
                <div v-else class="w-full">
                    <input 
                    :value="editedName" 
                    @input="emit('update:editedName', $event.target.value)" 
                    class="w-full px-3 py-2 text-gray-800 rounded"
                    placeholder="Recipe name"
                    >
                </div>
                <div class="flex gap-2">
                    <button v-if="!isEditing" @click="emit('startEditing')"
                        class="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 transition-colors duration-200">
                        Edit
                    </button>
                    <template v-else>
                        <button @click="emit('saveEdit')"
                            class="px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700 transition-colors duration-200">
                            Save
                        </button>
                        <button @click="emit('cancelEdit')"
                            class="px-4 py-2 bg-gray-600 text-white rounded hover:bg-gray-700 transition-colors duration-200">
                            Cancel
                        </button>
                    </template>
                    <button v-if="!isEditing" @click="emit('handleDelete')"
                        class="px-4 py-2 bg-red-600 text-white rounded hover:bg-red-700 transition-colors duration-200">
                        Delete
                    </button>
                </div>
            </div>
        </div>
</template>

<script setup>
const emit = defineEmits(['update:editedName', 'startEditing', 'cancelEdit', 'saveEdit', 'handleDelete'])
const props = defineProps(['editedName', 'recipe', 'isEditing'])

</script>