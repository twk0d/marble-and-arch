<script setup lang="ts">
const props = defineProps<{
  propertyId: string
  existingImages?: { id: string, url: string, active: boolean }[]
}>()

const emit = defineEmits(['upload-success', 'image-status-change', 'image-reorder'])
const toast = useToast()
const isUploading = ref(false)

const auth = useAuthStore()

async function onUpload(files: File[]) {
  if (!files || files.length === 0) return

  isUploading.value = true
  
  if (props.propertyId === 'styleguide-mock-id') {
      setTimeout(() => {
          toast.add({ title: 'Sucesso', description: 'Imagens fictícias enviadas.', color: 'success' })
          emit('upload-success')
          isUploading.value = false
      }, 1000)
      return;
  }

  try {
    for (const file of files) {
      const formData = new FormData()
      formData.append('image', file)

      await $fetch(`/api/v1/property-management/${props.propertyId}/image`, {
        method: 'POST',
        body: formData,
        headers: {
          Authorization: `Bearer ${auth.token}`
        }
      })
    }

    toast.add({
      title: 'Sucesso',
      description: 'Imagem(ns) enviada(s) com sucesso!',
      color: 'success'
    })
    
    emit('upload-success')
  } catch (error) {
    toast.add({
      title: 'Erro',
      description: 'Falha ao enviar imagem.',
      color: 'error'
    })
  } finally {
    isUploading.value = false
  }
}

async function toggleImageStatus(imageUUID: string, currentStatus: boolean) {
  if (props.propertyId === 'styleguide-mock-id') {
      emit('image-status-change', { id: imageUUID, active: !currentStatus })
      return
  }

  const action = currentStatus ? 'disable' : 'enable'
  try {
    await $fetch(`/api/v1/property-management/${props.propertyId}/image/${imageUUID}/${action}`, {
      method: 'PATCH',
      headers: {
        Authorization: `Bearer ${auth.token}`
      }
    })
    emit('image-status-change')
  } catch (error) {
    toast.add({
      title: 'Erro',
      description: 'Falha ao alterar status da imagem.',
      color: 'error'
    })
  }
}

async function moveImage(imageUUID: string, direction: 'up' | 'down') {
  if (props.propertyId === 'styleguide-mock-id') {
      emit('image-reorder', { id: imageUUID, direction })
      return
  }

  // Local placeholder logic until backend supports position attribute
  toast.add({
    title: 'Informação',
    description: `Reordenação (${direction}) solicitada. Funcionalidade aguardando endpoint de ordenação no backend.`,
    color: 'info'
  })
}
</script>

<template>
  <div class="space-y-6">
    <div class="flex items-center justify-between">
      <h3 class="text-h4">Imagens do Imóvel</h3>
    </div>

    <UFileUpload
      multiple
      accept="image/*"
      :loading="isUploading"
      @change="onUpload"
    >
      <template #description>
        Arraste e solte ou clique para enviar fotos do imóvel
      </template>
    </UFileUpload>

    <div v-if="existingImages && existingImages.length > 0" class="grid grid-cols-2 md:grid-cols-4 lg:grid-cols-6 gap-4">
      <div
        v-for="(image, index) in existingImages"
        :key="image.id"
        class="relative aspect-square rounded-lg overflow-hidden border border-border group"
      >
        <NuxtImg :src="image.url" class="w-full h-full object-cover" :class="{ 'grayscale opacity-50': !image.active }" />
        
        <div class="absolute inset-0 bg-overlay opacity-0 group-hover:opacity-100 transition-opacity flex flex-col items-center justify-center gap-2">
          <div class="flex gap-1">
            <UButton
              v-if="index > 0"
              size="xs"
              variant="solid"
              color="neutral"
              icon="i-heroicons-arrow-left"
              @click="moveImage(image.id, 'up')"
            />
            <UButton
              v-if="index < existingImages.length - 1"
              size="xs"
              variant="solid"
              color="neutral"
              icon="i-heroicons-arrow-right"
              @click="moveImage(image.id, 'down')"
            />
          </div>
          <UButton
            size="xs"
            :color="image.active ? 'error' : 'success'"
            :icon="image.active ? 'i-heroicons-eye-slash' : 'i-heroicons-eye'"
            @click="toggleImageStatus(image.id, image.active)"
          />
        </div>
        
        <UBadge v-if="index === 0" class="absolute top-2 left-2" color="primary" size="xs">Principal</UBadge>
      </div>
    </div>
    
    <div v-else class="text-center py-12 border-2 border-dashed border-border rounded-lg">
      <UIcon name="i-heroicons-photo" class="w-12 h-12 mx-auto opacity-20 mb-2" />
      <p class="text-body-muted">Nenhuma imagem enviada.</p>
    </div>
  </div>
</template>
