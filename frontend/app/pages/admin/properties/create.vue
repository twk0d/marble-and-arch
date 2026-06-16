<script setup lang="ts">
import { usePropertyFormStore } from '~/stores/propertyForm'
import { PropertyType, Amenity } from '~/types'

definePageMeta({
  layout: 'default'
})

const { t } = useI18n()
const store = usePropertyFormStore()
const toast = useToast()

const steps = [
  { label: 'Básico', description: 'Tipo e Localização' },
  { label: 'Características', description: 'Tamanhos e Cômodos' },
  { label: 'Comodidades', description: 'Áreas de Lazer' },
  { label: 'Mídia', description: 'Fotos e Vídeos' },
  { label: 'Valores', description: 'Preço e Taxas' },
  { label: 'Revisão', description: 'Publicar Anúncio' }
]

const propertyTypeOptions = Object.values(PropertyType).map(type => ({
  label: t(`propertyType.${type}`),
  value: type
}))

const amenityOptions = Object.values(Amenity).map(amenity => ({
  label: t(`amenity.${amenity}`),
  value: amenity
}))

async function submitProperty() {
  try {
    const response = await $fetch<any>('/api/v1/properties/submit', {
      method: 'POST',
      headers: { Authorization: `Bearer ${useCookie('auth_token').value}` },
      body: store.formData
    })
    
    if (response.success || response) {
      toast.add({ title: 'Sucesso', description: 'Imóvel enviado com sucesso!', color: 'success' })
      store.resetForm()
      navigateTo('/admin/dashboard')
    } else {
      throw new Error(response.message || 'Erro ao publicar')
    }
  } catch (error: any) {
    const errorMsg = error.response?._data?.message || error.message || 'Falha ao publicar imóvel.'
    toast.add({ title: 'Erro', description: errorMsg, color: 'error' })
  }
}
</script>

<template>
  <div class="max-w-4xl mx-auto py-12 px-4 sm:px-6 lg:px-8 min-h-[80vh] flex flex-col">
    <!-- Header -->
    <div class="mb-10 text-center">
      <h1 class="text-h2 text-foreground mb-2">Anunciar Imóvel</h1>
      <p class="text-body-lg text-text-dimmed">Preencha os dados do imóvel em nosso assistente simplificado.</p>
    </div>

    <!-- Stepper Progress -->
    <div class="mb-12">
      <div class="flex items-center justify-between relative">
        <div class="absolute left-0 top-1/2 -translate-y-1/2 w-full h-1 bg-muted rounded-full z-0" />
        <div 
          class="absolute left-0 top-1/2 -translate-y-1/2 h-1 bg-primary rounded-full z-0 transition-all duration-500"
          :style="{ width: `${((store.currentStep - 1) / 5) * 100}%` }"
        />
        
        <div 
          v-for="(step, index) in steps" 
          :key="index"
          class="relative z-10 flex flex-col items-center gap-2 bg-background px-2"
        >
          <div 
            class="w-10 h-10 rounded-full flex items-center justify-center font-semibold border-2 transition-colors"
            :class="[
              store.currentStep > index + 1 ? 'bg-primary border-primary text-white' : 
              store.currentStep === index + 1 ? 'border-primary text-primary bg-background' : 
              'border-border text-text-dimmed bg-background'
            ]"
          >
            <UIcon v-if="store.currentStep > index + 1" name="i-heroicons-check" class="w-6 h-6" />
            <span v-else>{{ index + 1 }}</span>
          </div>
          <span class="text-xs font-medium hidden sm:block" :class="store.currentStep === index + 1 ? 'text-primary' : 'text-text-dimmed'">
            {{ step.label }}
          </span>
        </div>
      </div>
    </div>

    <!-- Form Area -->
    <UCard class="flex-1 bg-card border-border shadow-sm flex flex-col" :ui="{ body: 'flex-1 flex flex-col p-6 sm:p-10' }">
      
      <!-- STEP 1: Basic Info -->
      <div v-if="store.currentStep === 1" class="space-y-6 flex-1">
        <h3 class="text-h3 border-b border-border pb-4 mb-6">Informações Básicas</h3>
        <UFormField label="Tipo de Imóvel" required>
          <USelectMenu v-model="store.formData.type" :items="propertyTypeOptions" value-attribute="value" size="lg" />
        </UFormField>
        
        <div class="grid grid-cols-1 sm:grid-cols-2 gap-4">
          <UFormField label="CEP">
            <UInput v-model="store.formData.address.zipCode" placeholder="00000-000" />
          </UFormField>
          <UFormField label="Cidade">
            <UInput v-model="store.formData.address.city" />
          </UFormField>
        </div>
        
        <UFormField label="Logradouro">
          <UInput v-model="store.formData.address.street" placeholder="Nome da rua/avenida" />
        </UFormField>
      </div>

      <!-- STEP 2: Details -->
      <div v-if="store.currentStep === 2" class="space-y-6 flex-1">
        <h3 class="text-h3 border-b border-border pb-4 mb-6">Características</h3>
        <div class="grid grid-cols-2 sm:grid-cols-4 gap-4">
          <UFormField label="Quartos">
            <UInput v-model="store.formData.details.bedrooms" type="number" />
          </UFormField>
          <UFormField label="Suítes">
            <UInput v-model="store.formData.details.suites" type="number" />
          </UFormField>
          <UFormField label="Banheiros">
            <UInput v-model="store.formData.details.bathrooms" type="number" />
          </UFormField>
          <UFormField label="Vagas">
            <UInput v-model="store.formData.details.parkingSpaces" type="number" />
          </UFormField>
        </div>
        
        <div class="grid grid-cols-1 sm:grid-cols-2 gap-4">
          <UFormField label="Área Total (m²)">
            <UInput v-model="store.formData.details.totalAreaValue" type="number" />
          </UFormField>
          <UFormField label="Área Útil (m²)">
            <UInput v-model="store.formData.details.builtAreaValue" type="number" />
          </UFormField>
        </div>

        <UFormField label="Descrição do Imóvel" description="Destaque os principais diferenciais arquitetônicos e de luxo.">
          <UTextarea v-model="store.formData.details.description" :rows="5" />
        </UFormField>
      </div>

      <!-- STEP 3: Amenities -->
      <div v-if="store.currentStep === 3" class="space-y-6 flex-1">
        <h3 class="text-h3 border-b border-border pb-4 mb-6">Comodidades</h3>
        <p class="text-body-sm text-text-dimmed mb-4">Selecione as áreas de lazer e infraestrutura disponíveis.</p>
        
        <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
          <UCheckbox 
            v-for="amenity in amenityOptions" 
            :key="amenity.value"
            v-model="store.formData.amenities"
            :value="amenity.value"
            :label="amenity.label"
          />
        </div>
      </div>

      <!-- STEP 4: Media -->
      <div v-if="store.currentStep === 4" class="space-y-6 flex-1">
        <h3 class="text-h3 border-b border-border pb-4 mb-6">Mídia</h3>
        <div class="border-2 border-dashed border-border rounded-xl p-12 flex flex-col items-center justify-center text-center bg-muted/50">
          <UIcon name="i-heroicons-photo" class="w-12 h-12 text-text-dimmed mb-4" />
          <h4 class="text-h5 text-foreground mb-2">Arraste fotos ou clique para fazer upload</h4>
          <p class="text-xs text-text-dimmed max-w-sm mb-6">A primeira foto será utilizada como capa do anúncio. Utilize imagens de alta resolução.</p>
          <UButton color="neutral" variant="outline">Selecionar Arquivos</UButton>
        </div>
        <!-- Mocked Image list -->
        <div class="grid grid-cols-2 sm:grid-cols-4 gap-4 mt-6">
          <div class="aspect-square bg-muted rounded-lg border border-border flex items-center justify-center text-xs text-text-dimmed">Foto 1 (Capa)</div>
          <div class="aspect-square bg-muted rounded-lg border border-border flex items-center justify-center text-xs text-text-dimmed">Foto 2</div>
        </div>
      </div>

      <!-- STEP 5: Values -->
      <div v-if="store.currentStep === 5" class="space-y-6 flex-1">
        <h3 class="text-h3 border-b border-border pb-4 mb-6">Valores</h3>
        <UFormField label="Valor de Venda (R$)" required size="xl">
          <UInput v-model="store.formData.price.amount" type="number" placeholder="Ex: 5000000" />
        </UFormField>
        
        <div class="grid grid-cols-1 sm:grid-cols-2 gap-4">
          <UFormField label="Valor do Condomínio (R$/mês)">
            <UInput v-model="store.formData.price.condominiumFee" type="number" />
          </UFormField>
          <UFormField label="IPTU (R$/mensal)">
            <UInput v-model="store.formData.price.iptu" type="number" />
          </UFormField>
        </div>
      </div>

      <!-- STEP 6: Review -->
      <div v-if="store.currentStep === 6" class="space-y-6 flex-1">
        <h3 class="text-h3 border-b border-border pb-4 mb-6">Revisão e Publicação</h3>
        <UAlert 
          icon="i-heroicons-information-circle" 
          color="primary" 
          variant="subtle"
          title="Quase lá!" 
          description="Revise os dados abaixo. Após publicado, o imóvel estará imediatamente disponível no catálogo." 
        />
        <div class="bg-muted p-6 rounded-xl mt-6 space-y-2 text-sm">
          <p><strong>Tipo:</strong> {{ t(`propertyType.${store.formData.type}`) }}</p>
          <p><strong>Local:</strong> {{ store.formData.address.street }}, {{ store.formData.address.city }}</p>
          <p><strong>Valor:</strong> R$ {{ store.formData.price.amount }}</p>
          <p><strong>Comodidades Selecionadas:</strong> {{ store.formData.amenities.length }}</p>
        </div>
      </div>

      <!-- Navigation Footer -->
      <div class="flex items-center justify-between pt-8 mt-8 border-t border-border">
        <UButton 
          variant="ghost" 
          color="neutral" 
          @click="store.prevStep"
          :disabled="store.currentStep === 1"
          icon="i-heroicons-arrow-left"
        >
          Voltar
        </UButton>
        
        <UButton 
          v-if="store.currentStep < 6"
          color="primary" 
          @click="store.nextStep"
          trailing-icon="i-heroicons-arrow-right"
        >
          Próximo Passo
        </UButton>
        
        <UButton 
          v-else
          color="primary" 
          size="lg"
          icon="i-heroicons-check"
          class="shadow-ambient"
          @click="submitProperty"
        >
          Publicar Anúncio
        </UButton>
      </div>

    </UCard>
  </div>
</template>
