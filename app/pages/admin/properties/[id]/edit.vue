<script setup lang="ts">
import PropertyForm from '~/components/property/PropertyForm.vue'
import ImageUpload from '~/components/property/ImageUpload.vue'
import type { PropertyDTO, CreatePropertyRequest } from '~/types'

definePageMeta({
  middleware: 'auth'
})

const route = useRoute()
const toast = useToast()
const propertyId = route.params.id as string

const auth = useAuthStore()
const { data: property, pending, error, refresh } = await useFetch<PropertyDTO>(`/api/v1/property-management/${propertyId}`, {
  headers: {
    Authorization: `Bearer ${auth.token}`
  }
})

const initialData = computed<Partial<CreatePropertyRequest> | null>(() => {
  if (!property.value) return null
  
  return {
    propertyType: property.value.type,
    street: property.value.address.street,
    number: property.value.address.number,
    neighborhood: property.value.address.neighborhood,
    city: property.value.address.city,
    state: property.value.address.state,
    postalCode: property.value.address.zipCode,
    complement: property.value.address.complement || '',
    priceAmount: property.value.price.amount,
    priceCurrency: property.value.price.currency,
    details: property.value.details
  }
})

async function onUpdate(data: CreatePropertyRequest) {
  try {
    await $fetch(`/api/v1/property-management/${propertyId}`, {
      method: 'PATCH',
      body: data,
      headers: {
        Authorization: `Bearer ${auth.token}`
      }
    })
    
    toast.add({
      title: 'Sucesso',
      description: 'Imóvel atualizado com sucesso!',
      color: 'success'
    })
    
    navigateTo('/admin/dashboard')
  } catch (error) {
    toast.add({
      title: 'Erro',
      description: 'Não foi possível atualizar o imóvel.',
      color: 'error'
    })
  }
}
</script>

<template>
  <UContainer class="py-12 space-y-12">
    <div>
      <UPageHeader
        title="Editar Imóvel"
        description="Atualize as informações do imóvel selecionado."
        class="mb-12"
      />

      <div v-if="pending" class="space-y-8">
        <USkeleton class="h-40 w-full" />
        <USkeleton class="h-80 w-full" />
      </div>

      <div v-else-if="error" class="text-center py-20">
        <h2 class="text-2xl font-bold font-sora">Erro ao carregar imóvel</h2>
        <UButton to="/admin/dashboard" class="mt-4">Voltar ao Dashboard</UButton>
      </div>

      <PropertyForm
        v-else-if="initialData"
        :initial-data="initialData"
        is-editing
        @submit="onUpdate"
      />
    </div>

    <UDivider v-if="property" />

    <div v-if="property">
      <ImageUpload
        :property-id="propertyId"
        :existing-images="property.images"
        @upload-success="refresh"
        @image-status-change="refresh"
      />
    </div>
  </UContainer>
</template>
