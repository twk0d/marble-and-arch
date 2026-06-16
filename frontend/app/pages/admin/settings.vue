<script setup lang="ts">
import { useToast } from '#imports'

definePageMeta({
  layout: 'admin'
})

const toast = useToast()

const items = [
  { label: 'Geral', icon: 'i-heroicons-building-office', slot: 'general' },
  { label: 'Integrações', icon: 'i-heroicons-puzzle-piece', slot: 'integrations' },
  { label: 'Notificações', icon: 'i-heroicons-bell', slot: 'notifications' }
]

const generalState = reactive({
  companyName: 'Marble & Arch',
  email: 'contato@marbleandarch.com',
  phone: '+55 11 99999-9999',
  address: 'Av. Brigadeiro Faria Lima, 3477 - Itaim Bibi, São Paulo - SP',
  website: 'https://marbleandarch.com'
})

const integrations = ref([
  { name: 'RD Station Marketing', description: 'Sincronização de Leads e automação de e-mails.', status: 'connected' },
  { name: 'Zapier', description: 'Automação de processos e conexões externas.', status: 'disconnected' },
  { name: 'WhatsApp Business API', description: 'Atendimento integrado via Meta.', status: 'connected' }
])

const notifState = reactive({
  newLeads: true,
  propertyReviews: true,
  weeklyReports: false
})

function saveGeneral() {
  toast.add({ title: 'Configurações Salvas', description: 'Os dados da empresa foram atualizados com sucesso.', color: 'success' })
}
</script>

<template>
  <div class="space-y-6 max-w-4xl">
    <div>
      <h1 class="text-h2 font-playfair text-foreground">Configurações Globais</h1>
      <p class="text-body-base text-text-dimmed mt-1">Gerencie os parâmetros globais da plataforma e conexões externas.</p>
    </div>

    <UTabs :items="items" class="mt-8">
      <template #general>
        <UCard class="mt-4" :ui="{ ring: 'ring-1 ring-border shadow-sm' }">
          <div class="space-y-6">
            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
              <UFormField label="Nome da Empresa">
                <UInput v-model="generalState.companyName" class="w-full" />
              </UFormField>
              <UFormField label="E-mail de Contato">
                <UInput v-model="generalState.email" type="email" class="w-full" />
              </UFormField>
              <UFormField label="Telefone Principal">
                <UInput v-model="generalState.phone" class="w-full" />
              </UFormField>
              <UFormField label="Website">
                <UInput v-model="generalState.website" class="w-full" />
              </UFormField>
            </div>
            <UFormField label="Endereço Completo">
              <UInput v-model="generalState.address" class="w-full" />
            </UFormField>
            
            <div class="flex justify-end pt-4 border-t border-border">
              <UButton color="primary" class="shadow-ambient" @click="saveGeneral">Salvar Alterações</UButton>
            </div>
          </div>
        </UCard>
      </template>

      <template #integrations>
        <UCard class="mt-4" :ui="{ ring: 'ring-1 ring-border shadow-sm' }">
          <div class="space-y-4">
            <div v-for="int in integrations" :key="int.name" class="flex items-center justify-between p-4 border border-border rounded-lg bg-muted/50">
              <div>
                <h4 class="text-sm font-medium text-foreground">{{ int.name }}</h4>
                <p class="text-xs text-text-dimmed mt-1">{{ int.description }}</p>
              </div>
              <UButton 
                :color="int.status === 'connected' ? 'error' : 'primary'" 
                :variant="int.status === 'connected' ? 'soft' : 'solid'"
                size="sm"
              >
                {{ int.status === 'connected' ? 'Desconectar' : 'Conectar' }}
              </UButton>
            </div>
          </div>
        </UCard>
      </template>

      <template #notifications>
        <UCard class="mt-4" :ui="{ ring: 'ring-1 ring-border shadow-sm' }">
          <div class="space-y-6">
            <h3 class="text-h5 text-foreground font-playfair">Preferências de Alertas</h3>
            
            <div class="space-y-4">
              <div class="flex items-center justify-between">
                <div>
                  <p class="text-sm font-medium text-foreground">Novos Leads</p>
                  <p class="text-xs text-text-dimmed">Notificar por e-mail quando um novo cliente se cadastrar.</p>
                </div>
                <USwitch v-model="notifState.newLeads" />
              </div>
              
              <div class="flex items-center justify-between">
                <div>
                  <p class="text-sm font-medium text-foreground">Aprovação de Imóveis</p>
                  <p class="text-xs text-text-dimmed">Receber alertas de anúncios aguardando moderação.</p>
                </div>
                <USwitch v-model="notifState.propertyReviews" />
              </div>
              
              <div class="flex items-center justify-between">
                <div>
                  <p class="text-sm font-medium text-foreground">Relatórios Semanais</p>
                  <p class="text-xs text-text-dimmed">Resumo de acessos e VGV enviado toda segunda-feira.</p>
                </div>
                <USwitch v-model="notifState.weeklyReports" />
              </div>
            </div>
          </div>
        </UCard>
      </template>
    </UTabs>
  </div>
</template>
