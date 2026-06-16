<script setup lang="ts">
import { useToast } from '#imports'

definePageMeta({
  layout: 'admin'
})

const toast = useToast()

const { data: response, refresh } = useFetch<any>('/api/v1/properties', {
  headers: {
    Authorization: `Bearer ${useCookie('auth_token').value}`
  }
})

const properties = computed(() => {
  if (response.value && response.value.success && response.value.data?.content) {
    return response.value.data.content.map((p: any) => ({
      id: p.id,
      title: p.title || 'Imóvel sem título',
      broker: p.brokerName || 'Corretor Desconhecido',
      date: p.createdAt ? new Date(p.createdAt).toLocaleDateString() : 'N/A',
      price: new Intl.NumberFormat('pt-BR', { style: 'currency', currency: p.price?.currency || 'BRL' }).format(p.price?.amount || 0),
      status: p.status || 'pending'
    }))
  }
  return [
    {
      id: 'PRP-2023-019',
      title: 'Cobertura Duplex Vila Nova Conceição',
      broker: 'Carolina Mendes',
      date: '10/11/2026',
      price: 'R$ 8.500.000',
      status: 'pending'
    },
    {
      id: 'PRP-2023-020',
      title: 'Casa de Condomínio Fazenda Boa Vista',
      broker: 'Thiago Silva',
      date: '09/11/2026',
      price: 'R$ 15.000.000',
      status: 'pending'
    }
  ]
})

const columns = [
  { accessorKey: 'id', header: 'Referência' },
  { accessorKey: 'title', header: 'Imóvel' },
  { accessorKey: 'broker', header: 'Corretor Responsável' },
  { accessorKey: 'date', header: 'Data de Envio' },
  { accessorKey: 'price', header: 'Valor Sugerido' },
  { id: 'actions', header: 'Ações' }
]

const isRejectModalOpen = ref(false)
const selectedProperty = ref<any>(null)
const rejectReason = ref('')

function openRejectModal(property: any) {
  selectedProperty.value = property
  isRejectModalOpen.value = true
}

async function confirmReject() {
  try {
    const res = await $fetch<any>(`/api/v1/broker/properties/${selectedProperty.value.id}/reject`, {
      method: 'POST',
      headers: { Authorization: `Bearer ${useCookie('auth_token').value}` },
      body: { reason: rejectReason.value }
    })
    
    if (res.success || res) {
      isRejectModalOpen.value = false
      rejectReason.value = ''
      toast.add({
        title: 'Anúncio Rejeitado',
        description: `O anúncio ${selectedProperty.value.id} foi rejeitado e o corretor notificado.`,
        color: 'error'
      })
      refresh()
    }
  } catch (error: any) {
    toast.add({ title: 'Erro', description: error.response?._data?.message || error.message || 'Erro ao rejeitar', color: 'error' })
  }
}

async function approveProperty(property: any) {
  try {
    const res = await $fetch<any>(`/api/v1/broker/properties/${property.id}/approve`, {
      method: 'POST',
      headers: { Authorization: `Bearer ${useCookie('auth_token').value}` }
    })
    
    if (res.success || res) {
      toast.add({
        title: 'Anúncio Aprovado',
        description: `O anúncio ${property.id} já está público no catálogo.`,
        color: 'success'
      })
      refresh()
    }
  } catch (error: any) {
    toast.add({ title: 'Erro', description: error.response?._data?.message || error.message || 'Erro ao aprovar', color: 'error' })
  }
}
</script>

<template>
  <div class="space-y-6">
    <div class="flex items-center justify-between">
      <div>
        <h1 class="text-h2 font-playfair text-foreground">Aprovação de Anúncios</h1>
        <p class="text-body-base text-text-dimmed mt-1">Analise e publique os imóveis enviados pelos corretores parceiros.</p>
      </div>
    </div>

    <!-- Review Table -->
    <UCard :ui="{ ring: 'ring-1 ring-border shadow-sm', body: { padding: '' } }">
      <UTable :data="properties" :columns="columns">
        <template #title-cell="{ row }">
          <span class="font-medium text-foreground">{{ row.original.title }}</span>
        </template>
        
        <template #actions-cell="{ row }">
          <div class="flex items-center justify-end gap-2">
            <UButton size="xs" color="neutral" variant="ghost" icon="i-heroicons-eye" title="Ver Detalhes" />
            <UButton size="xs" color="error" variant="soft" icon="i-heroicons-x-mark" title="Rejeitar" @click="openRejectModal(row.original)">
              Rejeitar
            </UButton>
            <UButton size="xs" color="success" variant="solid" icon="i-heroicons-check" title="Aprovar" @click="approveProperty(row.original)">
              Aprovar
            </UButton>
          </div>
        </template>
        
        <template #empty-state>
          <div class="py-12 flex flex-col items-center justify-center text-center">
            <UIcon name="i-heroicons-clipboard-document-check" class="w-12 h-12 text-muted-foreground mb-4" />
            <h3 class="text-body-lg font-medium text-foreground">Tudo em dia!</h3>
            <p class="text-sm text-text-dimmed">Não há novos imóveis aguardando revisão no momento.</p>
          </div>
        </template>
      </UTable>
    </UCard>

    <!-- Reject Modal -->
    <UModal v-model:open="isRejectModalOpen" title="Rejeitar Anúncio" description="Forneça o motivo da rejeição para o corretor ajustar.">
      <template #body>
        <div class="space-y-4">
          <div v-if="selectedProperty" class="p-3 bg-muted rounded-lg border border-border">
            <p class="text-sm font-medium">{{ selectedProperty.title }}</p>
            <p class="text-xs text-text-dimmed">Ref: {{ selectedProperty.id }} • Corretor: {{ selectedProperty.broker }}</p>
          </div>
          
          <UFormField label="Motivo da Rejeição">
            <UTextarea v-model="rejectReason" placeholder="Ex: Fotos com baixa resolução e endereço incompleto..." rows="4" class="w-full" />
          </UFormField>
          
          <div class="flex justify-end gap-3 mt-4">
            <UButton color="neutral" variant="ghost" @click="isRejectModalOpen = false">Cancelar</UButton>
            <UButton color="error" variant="solid" :disabled="!rejectReason" @click="confirmReject">Confirmar Rejeição</UButton>
          </div>
        </div>
      </template>
    </UModal>
  </div>
</template>
