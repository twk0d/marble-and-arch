<script setup lang="ts">
import { PropertyType } from '~/types'
import type { CreatePropertyRequest } from '~/types'
import { ViaCepAdapter } from '~/services/address/ViaCepAdapter'
import type { AddressLookupAdapter } from '~/services/address/AddressLookupAdapter'

const { t } = useI18n()

const props = defineProps<{
  initialData?: Partial<CreatePropertyRequest>
  isEditing?: boolean
}>()

const emit = defineEmits(['submit'])

const addressAdapter: AddressLookupAdapter = new ViaCepAdapter()
const isLoadingAddress = ref(false)

const state = reactive<CreatePropertyRequest>({
  propertyType: PropertyType.HOUSE,
  street: '',
  number: '',
  neighborhood: '',
  city: '',
  state: '',
  postalCode: '',
  complement: '',
  priceAmount: 0,
  priceCurrency: 'BRL',
  details: {
    bedrooms: 0,
    suites: 0,
    bathrooms: 0,
    parkingSpaces: 0,
    totalAreaValue: 0,
    totalAreaUnit: 'SQUARE_METERS',
    builtAreaValue: 0,
    builtAreaUnit: 'SQUARE_METERS',
    yearBuilt: new Date().getFullYear(),
    description: '',
    hasGarage: false,
    hasPool: false,
    hasBalcony: false
  },
  ...props.initialData
})

const activeStep = ref(0)
const steps = [
  { label: 'Informações Básicas', icon: 'i-heroicons-information-circle' },
  { label: 'Localização', icon: 'i-heroicons-map-pin' },
  { label: 'Características', icon: 'i-heroicons-home' },
  { label: 'Finalização', icon: 'i-heroicons-check-badge' }
]

const propertyTypeOptions = Object.values(PropertyType).map(type => ({
  label: t(`propertyType.${type}`),
  value: type,
  icon: 'i-heroicons-building-office'
}))

const currencyOptions = [
  { label: 'Real (BRL)', value: 'BRL' },
  { label: 'Dólar (USD)', value: 'USD' }
]

watch(() => state.postalCode, async (newCep) => {
  if (!newCep) return

  const cleanCep = newCep.replace(/\D/g, '')
  if (cleanCep.length === 8) {
    isLoadingAddress.value = true
    const address = await addressAdapter.lookup(cleanCep)
    if (address) {
      state.street = address.street
      state.neighborhood = address.neighborhood
      state.city = address.city
      state.state = address.state
    }
    isLoadingAddress.value = false
  }
})

function handleSubmit() {
  emit('submit', state)
}

function nextStep() {
  if (activeStep.value < steps.length - 1) {
    activeStep.value++
  }
}

function prevStep() {
  if (activeStep.value > 0) {
    activeStep.value--
  }
}
</script>

<template>
  <div class="space-y-10">
    <!-- Stepper Navigation -->
    <UStepper 
      v-model="activeStep" 
      :items="steps" 
      class="mb-12"
      :ui="{ 
        separator: 'bg-neutral-300 dark:bg-neutral-700'
      }"
    />

    <UForm :state="state" @submit="handleSubmit" class="space-y-8">
      
      <!-- Passo 1: Informações Básicas -->
      <div v-if="activeStep === 0" class="space-y-8 animate-in fade-in slide-in-from-bottom-4 duration-500">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-8">
          <UFormField label="Tipo de Imóvel" name="propertyType" help="Selecione a categoria que melhor descreve o imóvel.">
            <USelect
              v-model="state.propertyType"
              :items="propertyTypeOptions"
              icon="i-heroicons-home-modern"
              size="lg"
              class="w-full"
            />
          </UFormField>

          <div class="grid grid-cols-3 gap-4">
            <UFormField label="Moeda" name="priceCurrency" class="col-span-1">
              <USelect
                v-model="state.priceCurrency"
                :items="currencyOptions"
                size="lg"
              />
            </UFormField>
            <UFormField label="Valor de Venda" name="priceAmount" class="col-span-2">
              <UInput 
                v-model="state.priceAmount" 
                type="number" 
                step="0.01" 
                size="lg"
                icon="i-heroicons-currency-dollar"
                placeholder="0,00"
              />
            </UFormField>
          </div>
        </div>
        
        <UCard class="bg-muted border-dashed border-border">
          <p class="text-body-sm text-neutral-500">
            Dica: Certifique-se de que o valor está atualizado conforme o mercado local.
          </p>
        </UCard>
      </div>

      <!-- Passo 2: Localização -->
      <div v-if="activeStep === 1" class="space-y-8 animate-in fade-in slide-in-from-bottom-4 duration-500">
        <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
          <UFormField label="CEP" name="postalCode">
            <UInput 
              v-model="state.postalCode" 
              :loading="isLoadingAddress" 
              icon="i-heroicons-magnifying-glass"
              placeholder="00000-000"
              size="lg"
            />
          </UFormField>

          <UFormField label="Rua / Logradouro" name="street" class="md:col-span-2">
            <UInput v-model="state.street" size="lg" placeholder="Av. Principal..." />
          </UFormField>

          <UFormField label="Número" name="number">
            <UInput v-model="state.number" size="lg" placeholder="123" />
          </UFormField>

          <UFormField label="Complemento" name="complement">
            <UInput v-model="state.complement" size="lg" placeholder="Apto 402, Bloco B..." />
          </UFormField>

          <UFormField label="Bairro" name="neighborhood">
            <UInput v-model="state.neighborhood" size="lg" />
          </UFormField>

          <UFormField label="Cidade" name="city">
            <UInput v-model="state.city" size="lg" icon="i-heroicons-map" />
          </UFormField>

          <UFormField label="Estado" name="state">
            <UInput v-model="state.state" size="lg" />
          </UFormField>
        </div>
      </div>

      <!-- Passo 3: Características Físicas -->
      <div v-if="activeStep === 2" class="space-y-10 animate-in fade-in slide-in-from-bottom-4 duration-500">
        <div class="grid grid-cols-2 md:grid-cols-4 gap-6">
          <UFormField label="Quartos" name="details.bedrooms">
            <UInputNumber v-model="state.details.bedrooms" size="lg" />
          </UFormField>

          <UFormField label="Suítes" name="details.suites">
            <UInputNumber v-model="state.details.suites" size="lg" />
          </UFormField>

          <UFormField label="Banheiros" name="details.bathrooms">
            <UInputNumber v-model="state.details.bathrooms" size="lg" />
          </UFormField>

          <UFormField label="Vagas" name="details.parkingSpaces">
            <UInputNumber v-model="state.details.parkingSpaces" size="lg" />
          </UFormField>
        </div>

        <UDivider />

        <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
          <UFormField label="Área Total (m²)" name="details.totalAreaValue">
            <UInput v-model="state.details.totalAreaValue" type="number" size="lg" icon="i-heroicons-arrows-pointing-out" />
          </UFormField>

          <UFormField label="Área Construída (m²)" name="details.builtAreaValue">
            <UInput v-model="state.details.builtAreaValue" type="number" size="lg" icon="i-heroicons-home" />
          </UFormField>

          <UFormField label="Ano de Construção" name="details.yearBuilt">
            <UInput v-model="state.details.yearBuilt" type="number" size="lg" icon="i-heroicons-calendar" />
          </UFormField>
        </div>

        <div class="flex flex-wrap gap-6 p-4 bg-muted rounded-xl border border-border">
          <UCheckbox v-model="state.details.hasPool" label="Piscina" />
          <UCheckbox v-model="state.details.hasGarage" label="Garagem Coberta" />
          <UCheckbox v-model="state.details.hasBalcony" label="Varanda / Sacada" />
        </div>
      </div>

      <!-- Passo 4: Descrição e Finalização -->
      <div v-if="activeStep === 3" class="space-y-8 animate-in fade-in slide-in-from-bottom-4 duration-500">
        <UFormField label="Descrição Detalhada" name="details.description" help="Capriche na descrição para atrair mais interessados.">
          <UTextarea 
            v-model="state.details.description" 
            :rows="8" 
            placeholder="Descreva os pontos fortes do imóvel, localização, sol da manhã, vizinhança..."
            size="lg"
          />
        </UFormField>

        <UAlert
          icon="i-heroicons-check-circle"
          color="primary"
          variant="subtle"
          title="Tudo pronto!"
          description="Revise as informações nos passos anteriores antes de confirmar a publicação."
        />
      </div>

      <!-- Navigation Buttons -->
      <div class="flex justify-between items-center pt-8 border-t border-neutral-200 dark:border-neutral-800">
        <UButton
          v-if="activeStep > 0"
          variant="ghost"
          color="neutral"
          icon="i-heroicons-arrow-left"
          @click="prevStep"
        >
          Voltar
        </UButton>
        <div v-else />

        <div class="flex gap-4">
          <UButton
            v-if="activeStep < steps.length - 1"
            color="primary"
            trailing-icon="i-heroicons-arrow-right"
            size="lg"
            @click="nextStep"
          >
            Próximo Passo
          </UButton>
          
          <UButton
            v-else
            type="submit"
            color="primary"
            size="lg"
            class="px-8"
          >
            {{ isEditing ? 'Salvar Alterações' : 'Publicar Imóvel' }}
          </UButton>
        </div>
      </div>
    </UForm>
  </div>
</template>
