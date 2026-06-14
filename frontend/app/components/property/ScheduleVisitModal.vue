<script setup lang="ts">
import { ref, reactive } from 'vue'

const isOpen = defineModel('open', { type: Boolean, default: false })
const emit = defineEmits(['scheduled'])

const step = ref(1)
const isLoading = ref(false)

const state = reactive({
  date: '',
  time: '',
  name: '',
  email: '',
  phone: ''
})

const dates = ['15 Nov', '16 Nov', '17 Nov', '18 Nov']
const times = ['09:00', '10:30', '14:00', '16:30']

function nextStep() {
  if (step.value === 1) {
    step.value = 2;
  } else if (step.value === 2) {
    isLoading.value = true;
    setTimeout(() => {
      isLoading.value = false;
      step.value = 3;
      setTimeout(() => {
        isOpen.value = false;
        emit('scheduled');
        step.value = 1;
        state.date = '';
        state.time = '';
      }, 4000)
    }, 1500)
  }
}
</script>

<template>
  <UModal 
    v-model:open="isOpen" 
    :title="step === 3 ? 'Visita Confirmada!' : 'Agendar Visita'" 
    :description="step < 3 ? `Passo ${step} de 2` : ''"
  >
    <template #body>
      <!-- Step 1: Data e Hora -->
      <div v-if="step === 1" class="space-y-6">
        <div>
          <label class="block text-label-sm text-text-dimmed uppercase tracking-wider mb-3">Selecione o Dia</label>
          <div class="grid grid-cols-4 gap-2">
            <UButton 
              v-for="d in dates" :key="d" 
              :variant="state.date === d ? 'solid' : 'soft'" 
              :color="state.date === d ? 'primary' : 'neutral'"
              class="justify-center font-medium"
              @click="state.date = d"
            >
              {{ d.split(' ')[0] }}<br><span class="text-[10px] uppercase font-normal">{{ d.split(' ')[1] }}</span>
            </UButton>
          </div>
        </div>
        
        <div :class="{ 'opacity-50 pointer-events-none': !state.date, 'transition-opacity duration-300': true }">
          <label class="block text-label-sm text-text-dimmed uppercase tracking-wider mb-3">Selecione o Horário</label>
          <div class="grid grid-cols-4 gap-2">
            <UButton 
              v-for="t in times" :key="t" 
              :variant="state.time === t ? 'solid' : 'soft'" 
              :color="state.time === t ? 'primary' : 'neutral'"
              class="justify-center font-medium"
              @click="state.time = t"
            >
              {{ t }}
            </UButton>
          </div>
        </div>

        <div class="pt-4 mt-6">
          <UButton block color="primary" size="xl" :disabled="!state.date || !state.time" class="shadow-ambient" @click="nextStep">Continuar</UButton>
        </div>
      </div>

      <!-- Step 2: Dados Pessoais -->
      <div v-else-if="step === 2" class="space-y-4">
        <UFormField label="Nome Completo">
          <UInput v-model="state.name" placeholder="Ex: João da Silva" size="lg" class="w-full" />
        </UFormField>
        <UFormField label="E-mail">
          <UInput v-model="state.email" type="email" placeholder="seu@email.com" size="lg" class="w-full" />
        </UFormField>
        <UFormField label="Telefone / WhatsApp">
          <UInput v-model="state.phone" placeholder="(11) 90000-0000" size="lg" class="w-full" />
        </UFormField>
        
        <div class="pt-4 mt-6 flex gap-3">
          <UButton variant="ghost" color="neutral" size="lg" @click="step = 1">Voltar</UButton>
          <UButton block color="primary" class="flex-1 shadow-ambient" size="lg" :loading="isLoading" @click="nextStep">Confirmar</UButton>
        </div>
      </div>

      <!-- Step 3: Sucesso -->
      <div v-else class="text-center py-4">
        <div class="w-20 h-20 bg-success/10 rounded-full flex items-center justify-center mx-auto mb-6">
          <UIcon name="i-heroicons-check" class="w-10 h-10 text-success" />
        </div>
        <p class="text-body-base text-text-dimmed max-w-sm mx-auto leading-relaxed">
          Sua visita está agendada para <strong>{{ state.date }}</strong> às <strong>{{ state.time }}</strong>. 
          Enviamos os detalhes do imóvel e os dados do corretor para o seu e-mail.
        </p>
      </div>
    </template>
  </UModal>
</template>
