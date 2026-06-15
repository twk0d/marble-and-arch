<script setup lang="ts">
import { useToast } from '#imports'

definePageMeta({
  layout: 'admin'
})

const toast = useToast()

const faqs = [
  {
    label: 'Como aprovar um anúncio?',
    content: 'Acesse o menu "Aprovação de Anúncios", selecione o imóvel pendente, revise os dados e fotos, e clique no botão verde "Aprovar".'
  },
  {
    label: 'Esqueci minha senha de corretor, o que faço?',
    content: 'Na tela de login, clique em "Esqueci minha senha" para receber um link de redefinição no seu e-mail corporativo.'
  },
  {
    label: 'Como alterar minhas comissões?',
    content: 'As taxas de comissão são fixadas via contrato. Para renegociação, abra um chamado direcionado ao setor Financeiro.'
  },
  {
    label: 'Erro ao fazer upload de fotos 4K',
    content: 'Certifique-se de que cada imagem tenha no máximo 10MB. O sistema suporta JPG, PNG e WebP.'
  }
]

const ticketForm = reactive({
  subject: '',
  department: 'Suporte Técnico',
  message: ''
})

const departments = ['Suporte Técnico', 'Financeiro', 'Marketing', 'Jurídico']
const isSubmitting = ref(false)

function submitTicket() {
  isSubmitting.value = true
  setTimeout(() => {
    isSubmitting.value = false
    toast.add({
      title: 'Chamado Aberto',
      description: `Seu ticket para o departamento ${ticketForm.department} foi criado com sucesso. Em breve entraremos em contato.`,
      color: 'success'
    })
    ticketForm.subject = ''
    ticketForm.message = ''
  }, 1000)
}
</script>

<template>
  <div class="space-y-8 max-w-5xl">
    <div>
      <h1 class="text-h2 font-playfair text-foreground">Central de Suporte</h1>
      <p class="text-body-base text-text-dimmed mt-1">Precisa de ajuda? Consulte o FAQ ou abra um chamado interno.</p>
    </div>

    <div class="grid grid-cols-1 lg:grid-cols-2 gap-12">
      <!-- FAQ Section -->
      <section>
        <h2 class="text-h4 font-playfair text-foreground mb-6">Dúvidas Frequentes (FAQ)</h2>
        <UAccordion :items="faqs" color="neutral" variant="ghost" size="lg" class="bg-card rounded-lg border border-border shadow-sm" />
      </section>

      <!-- Ticket Form Section -->
      <section>
        <UCard :ui="{ ring: 'ring-1 ring-border shadow-sm' }">
          <template #header>
            <h2 class="text-h4 font-playfair text-foreground">Abrir Chamado</h2>
            <p class="text-xs text-text-dimmed mt-1">Preencha os dados abaixo para falar com a equipe M&A.</p>
          </template>

          <form @submit.prevent="submitTicket" class="space-y-4">
            <UFormField label="Assunto">
              <UInput v-model="ticketForm.subject" placeholder="Resumo do seu problema..." size="lg" class="w-full" required />
            </UFormField>
            
            <UFormField label="Departamento">
              <USelectMenu v-model="ticketForm.department" :options="departments" size="lg" class="w-full" />
            </UFormField>
            
            <UFormField label="Mensagem">
              <UTextarea v-model="ticketForm.message" placeholder="Descreva detalhadamente a sua solicitação..." rows="5" class="w-full" required />
            </UFormField>

            <div class="pt-4 mt-4 border-t border-border">
              <UButton type="submit" block color="primary" size="lg" :loading="isSubmitting" class="shadow-ambient">
                Enviar Solicitação
              </UButton>
            </div>
          </form>
        </UCard>
      </section>
    </div>
  </div>
</template>
