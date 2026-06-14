<script setup lang="ts">
const props = defineProps<{ propertyId: string }>();
const { t } = useI18n();
const state = reactive({
  name: '',
  email: '',
  phone: '',
  message: 'Olá, tenho interesse neste imóvel e gostaria de agendar uma visita.'
});

const isLoading = ref(false);

async function onSubmit() {
  try {
    isLoading.value = true;
    const response = await $fetch<any>('/api/v1/leads', {
      method: 'POST',
      body: {
        propertyId: props.propertyId,
        name: state.name,
        email: state.email,
        phone: state.phone,
        message: state.message
      }
    });

    if (response.success || response) {
      useToast().add({ title: 'Sucesso', description: 'Sua solicitação foi enviada. Um de nossos corretores entrará em contato em breve.', color: 'success' });
      state.name = '';
      state.email = '';
      state.phone = '';
      state.message = 'Olá, tenho interesse neste imóvel e gostaria de agendar uma visita.';
    } else {
      throw new Error(response.message || 'Erro ao enviar lead');
    }
  } catch (error: any) {
    const errorMsg = error.response?._data?.message || error.message || 'Ocorreu um erro.';
    useToast().add({ title: 'Erro', description: errorMsg, color: 'error' });
  } finally {
    isLoading.value = false;
  }
}
</script>

<template>
  <UCard class="bg-glass-sand shadow-ambient border border-white/20 dark:border-white/10" :ui="{ body: 'p-6 sm:p-8' }">
    <div class="mb-6">
      <h3 class="text-h4 text-foreground mb-2">Fale com um Especialista</h3>
      <p class="text-body-sm text-text-dimmed">Agende uma visita presencial ou receba o memorial descritivo completo.</p>
    </div>
    
    <UForm :state="state" @submit="onSubmit" class="space-y-4">
      <UFormField label="Seu Nome Completo">
        <UInput v-model="state.name" placeholder="Ex: João da Silva" size="lg" />
      </UFormField>
      
      <UFormField label="E-mail">
        <UInput v-model="state.email" type="email" placeholder="contato@exemplo.com" size="lg" />
      </UFormField>
      
      <UFormField label="Telefone / WhatsApp">
        <UInput v-model="state.phone" placeholder="(11) 90000-0000" size="lg" />
      </UFormField>
      
      <UFormField label="Mensagem">
        <UTextarea v-model="state.message" :rows="4" />
      </UFormField>
      
      <UButton type="submit" block color="primary" size="xl" class="mt-4 shadow-ambient">
        Solicitar Contato
      </UButton>
      
      <p class="text-xs text-center text-text-dimmed mt-4">
        Seus dados estão protegidos sob sigilo absoluto.
      </p>
    </UForm>
  </UCard>
</template>
