<script setup lang="ts">
import PropertyForm from '~/components/property/PropertyForm.vue'
import type { CreatePropertyRequest } from '~/types'

definePageMeta({
  middleware: 'auth'
})

const toast = useToast()

async function onCreate(data: CreatePropertyRequest) {
  const auth = useAuthStore()
  try {
    await $fetch('/api/v1/property-management', {
      method: 'POST',
      body: data,
      headers: {
        Authorization: `Bearer ${auth.token}`
      }
    })
    
    toast.add({
      title: 'Sucesso',
      description: 'Imóvel criado com sucesso!',
      color: 'success'
    })
    
    navigateTo('/admin/dashboard')
  } catch (error) {
    toast.add({
      title: 'Erro',
      description: 'Não foi possível criar o imóvel.',
      color: 'error'
    })
  }
}
</script>

<template>
  <UContainer class="py-12">
    <UPageHeader
      title="Novo Imóvel"
      description="Preencha os dados abaixo para cadastrar um novo imóvel na plataforma."
      class="mb-12"
    />

    <PropertyForm @submit="onCreate" />
  </UContainer>
</template>
