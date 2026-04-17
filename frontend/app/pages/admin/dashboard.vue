<script setup lang="ts">
import type { AdminDashboardDTO, PropertySummaryDTO } from '~/types'
import type { Page } from '~/types/page'

definePageMeta({
  middleware: 'auth',
  layout: false 
})

const { t } = useI18n()
const auth = useAuthStore()
const toast = useToast()

// Dashboard Stats
const { data: stats, pending: statsPending } = await useFetch<AdminDashboardDTO>('/api/v1/administration/dashboard', {
  headers: {
    Authorization: `Bearer ${auth.token}`
  }
})

// Tabs definition
const items = [{
  slot: 'properties',
  label: 'Gestão de Imóveis',
  icon: 'i-heroicons-home'
}, {
  slot: 'leads',
  label: 'Leads e Contatos',
  icon: 'i-heroicons-user-group'
}]

// Property List for management
const page = ref(1)
const { data: propertiesPage, pending: propertiesPending, refresh: refreshProperties } = await useFetch<Page<PropertySummaryDTO>>('/api/v1/property-management/search', {
  query: {
    page: computed(() => page.value - 1),
    size: 10
  },
  headers: {
    Authorization: `Bearer ${auth.token}`
  },
  watch: [page]
})

// Leads List
const leadsPageNum = ref(1)
const { data: leadsPage, pending: leadsPending, refresh: refreshLeads } = await useFetch<Page<LeadDTO>>('/api/v1/leads', {
  query: {
    page: computed(() => leadsPageNum.value - 1),
    size: 10
  },
  headers: {
    Authorization: `Bearer ${auth.token}`
  },
  watch: [leadsPageNum]
})

const columns = [
  { key: 'id', label: 'ID' },
  { key: 'type', label: 'Tipo' },
  { key: 'location', label: 'Localização' },
  { key: 'price', label: 'Preço' },
  { key: 'status', label: 'Status' },
  { key: 'actions', label: 'Ações' }
]

const leadColumns = [
  { key: 'createdAt', label: 'Data' },
  { key: 'customerName', label: 'Cliente' },
  { key: 'propertyId', label: 'Imóvel (ID)' },
  { key: 'status', label: 'Status' },
  { key: 'actions', label: 'Ações' }
]

async function togglePropertyStatus(property: PropertySummaryDTO, currentStatus: boolean) {
  const action = currentStatus ? 'disable' : 'enable'
  try {
    await $fetch(`/api/v1/property-management/${property.id}/${action}`, {
      method: 'PATCH',
      headers: {
        Authorization: `Bearer ${auth.token}`
      }
    })
    toast.add({ title: 'Sucesso', description: 'Status atualizado.', color: 'success' })
    refreshProperties()
  } catch (error) {
    toast.add({ title: 'Erro', description: 'Falha ao atualizar status.', color: 'error' })
  }
}

async function updateLeadStatus(leadId: string, status: string) {
  try {
    await $fetch(`/api/v1/leads/${leadId}/status`, {
      method: 'PATCH',
      body: { status },
      headers: {
        Authorization: `Bearer ${auth.token}`
      }
    })
    toast.add({ title: 'Sucesso', description: 'Status do lead atualizado.', color: 'success' })
    refreshLeads()
  } catch (error) {
    toast.add({ title: 'Erro', description: 'Falha ao atualizar lead.', color: 'error' })
  }
}

// Quick Price Edit
const isPriceModalOpen = ref(false)
const selectedProperty = ref<PropertySummaryDTO | null>(null)
const newPrice = ref(0)
const isUpdatingPrice = ref(false)

function openPriceModal(property: PropertySummaryDTO) {
  selectedProperty.value = property
  newPrice.value = property.priceAmount
  isPriceModalOpen.value = true
}

async function updatePrice() {
  if (!selectedProperty.value) return
  isUpdatingPrice.value = true
  
  try {
    await $fetch(`/api/v1/property-management/${selectedProperty.value.id}`, {
      method: 'PATCH',
      body: {
        priceAmount: newPrice.value,
        priceCurrency: selectedProperty.value.priceCurrency
      },
      headers: {
        Authorization: `Bearer ${auth.token}`
      }
    })
    
    toast.add({ title: 'Sucesso', description: 'Preço atualizado com sucesso.', color: 'success' })
    isPriceModalOpen.value = false
    refreshProperties()
  } catch (error) {
    toast.add({ title: 'Erro', description: 'Falha ao atualizar o preço.', color: 'error' })
  } finally {
    isUpdatingPrice.value = false
  }
}
</script>

<template>
  <UDashboardLayout>
    <UDashboardPanel grow>
      <UDashboardNavbar title="Painel Administrativo">
        <template #right>
          <UButton to="/admin/properties/create" icon="i-heroicons-plus" color="primary">
            Novo Imóvel
          </UButton>
          <UButton variant="ghost" color="neutral" icon="i-heroicons-arrow-left-on-rectangle" @click="auth.logout" />
        </template>
      </UDashboardNavbar>

      <UDashboardToolbar>
        <template #left>
          <span class="text-neutral-500 font-inter text-sm">
            Bem-vindo de volta, {{ auth.user?.email }}
          </span>
        </template>
      </UDashboardToolbar>

      <UDashboardPanelContent class="space-y-8">
        <!-- Stats Grid -->
        <div v-if="stats" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-5 gap-4">
          <UCard class="border-border">
            <template #header>
              <div class="flex items-center gap-2 text-body-muted text-sm">
                <UIcon name="i-heroicons-home" class="size-5 text-primary" />
                <span>Total de Imóveis</span>
              </div>
            </template>
            <div class="text-h3">{{ stats.totalProperties }}</div>
          </UCard>

          <UCard class="border-border">
            <template #header>
              <div class="flex items-center gap-2 text-body-muted text-sm">
                <UIcon name="i-heroicons-check-circle" class="size-5 text-success" />
                <span>Ativos</span>
              </div>
            </template>
            <div class="text-h3 text-success">{{ stats.activeProperties }}</div>
          </UCard>

          <UCard class="border-border">
            <template #header>
              <div class="flex items-center gap-2 text-body-muted text-sm">
                <UIcon name="i-heroicons-x-circle" class="size-5 text-error" />
                <span>Inativos</span>
              </div>
            </template>
            <div class="text-h3 text-error">{{ stats.inactiveProperties }}</div>
          </UCard>

          <UCard class="border-border">
            <template #header>
              <div class="flex items-center gap-2 text-body-muted text-sm">
                <UIcon name="i-heroicons-currency-dollar" class="size-5 text-primary" />
                <span>Valor de Mercado</span>
              </div>
            </template>
            <div class="text-h4 font-bold truncate">
              R$ {{ stats.totalMarketValue.toLocaleString('pt-BR') }}
            </div>
          </UCard>

          <UCard class="border-border">
            <template #header>
              <div class="flex items-center gap-2 text-body-muted text-sm">
                <UIcon name="i-heroicons-bolt" class="size-5 text-warning" />
                <span>Atividade (24h)</span>
              </div>
            </template>
            <div class="text-h3">{{ stats.recentActivitiesCount }}</div>
          </UCard>
        </div>

        <!-- Distributions Grid -->
        <div v-if="stats" class="grid grid-cols-1 lg:grid-cols-2 gap-4">
          <UCard title="Imóveis por Tipo" class="border-border">
            <div class="space-y-4">
              <div v-for="(count, type) in stats.distributionByType" :key="type" class="space-y-1">
                <div class="flex justify-between text-sm">
                  <span>{{ t(`propertyType.${type}`) }}</span>
                  <span class="text-body-bold">{{ count }}</span>
                </div>
                <UProgress :value="count" :max="stats.totalProperties" size="sm" color="primary" />
              </div>
            </div>
          </UCard>

          <UCard title="Imóveis por Cidade" class="border-border">
            <div class="space-y-4">
              <div v-for="(count, city) in stats.distributionByCity" :key="city" class="space-y-1">
                <div class="flex justify-between text-sm">
                  <span>{{ city }}</span>
                  <span class="text-body-bold">{{ count }}</span>
                </div>
                <UProgress :value="count" :max="stats.totalProperties" size="sm" color="success" />
              </div>
            </div>
          </UCard>
        </div>

        <!-- Management Tabs -->
        <UTabs :items="items" class="w-full">
          <template #properties>
            <UCard class="border-border">
              <UTable
                :rows="propertiesPage?.content"
                :columns="columns"
                :loading="propertiesPending"
              >
                <template #empty-state>
                  <UEmpty
                    title="Nenhum imóvel encontrado"
                    description="Você ainda não cadastrou nenhum imóvel ou a busca não retornou resultados."
                    icon="i-heroicons-home"
                  />
                </template>

                <template #type-cell="{ row }">
                  {{ t(`propertyType.${row.type}`) }}
                </template>

                <template #location-cell="{ row }">
                  {{ row.city }}, {{ row.state }}
                </template>

                <template #price-cell="{ row }">
                  <div class="flex items-center gap-2">
                    {{ row.priceCurrency }} {{ row.priceAmount.toLocaleString('pt-BR') }}
                    <UButton
                      size="xs"
                      variant="ghost"
                      color="neutral"
                      icon="i-heroicons-pencil"
                      @click="openPriceModal(row)"
                    />
                  </div>
                </template>

                <template #status-cell="{ row }">
                  <UBadge :color="row.active ? 'success' : 'error'" variant="subtle">
                    {{ row.active ? 'Ativo' : 'Inativo' }}
                  </UBadge>
                </template>

                <template #actions-cell="{ row }">
                  <div class="flex gap-2">
                    <UButton
                      size="xs"
                      variant="ghost"
                      color="neutral"
                      icon="i-heroicons-pencil-square"
                      :to="`/admin/properties/${row.id}/edit`"
                    />
                    <UButton
                      size="xs"
                      variant="ghost"
                      :color="row.active ? 'error' : 'success'"
                      :icon="row.active ? 'i-heroicons-pause-circle' : 'i-heroicons-play-circle'"
                      @click="togglePropertyStatus(row, row.active)"
                    />
                  </div>
                </template>
              </UTable>

              <template #footer>
                <div class="flex justify-center">
                  <UPagination
                    v-model="page"
                    :total="propertiesPage?.totalElements || 0"
                    :page-count="10"
                  />
                </div>
              </template>
            </UCard>
          </template>

          <template #leads>
            <UCard class="border-border">
              <UTable
                :rows="leadsPage?.content"
                :columns="leadColumns"
                :loading="leadsPending"
              >
                <template #empty-state>
                  <UEmpty
                    title="Nenhum lead encontrado"
                    description="Você ainda não recebeu contatos de interessados."
                    icon="i-heroicons-user-group"
                  />
                </template>

                <template #createdAt-cell="{ row }">
                  {{ new Date(row.createdAt).toLocaleDateString('pt-BR') }}
                </template>

                <template #customerName-cell="{ row }">
                  <div class="flex flex-col">
                    <span class="font-bold">{{ row.customerName }}</span>
                    <span class="text-xs text-neutral-500">{{ row.customerEmail }}</span>
                  </div>
                </template>

                <template #status-cell="{ row }">
                  <UBadge 
                    :color="row.status === 'NEW' ? 'primary' : row.status === 'CONTACTED' ? 'success' : 'neutral'" 
                    variant="subtle"
                  >
                    {{ row.status }}
                  </UBadge>
                </template>

                <template #actions-cell="{ row }">
                  <div class="flex gap-2">
                    <UButton
                      v-if="row.status === 'NEW'"
                      size="xs"
                      color="success"
                      variant="ghost"
                      icon="i-heroicons-check"
                      @click="updateLeadStatus(row.id, 'CONTACTED')"
                    />
                    <UButton
                      v-if="row.status !== 'ARCHIVED'"
                      size="xs"
                      color="neutral"
                      variant="ghost"
                      icon="i-heroicons-archive-box"
                      @click="updateLeadStatus(row.id, 'ARCHIVED')"
                    />
                  </div>
                </template>
              </UTable>

              <template #footer>
                <div class="flex justify-center">
                  <UPagination
                    v-model="leadsPageNum"
                    :total="leadsPage?.totalElements || 0"
                    :page-count="10"
                  />
                </div>
              </template>
            </UCard>
          </template>
        </UTabs>
      </UDashboardPanelContent>
    </UDashboardPanel>

    <!-- Quick Price Edit Modal -->
    <UModal v-model="isPriceModalOpen" title="Editar Preço">
      <template #body>
        <div class="p-4 space-y-4">
          <p class="text-sm text-neutral-500">
            Atualize o preço de venda para o imóvel ID: <span class="font-bold">{{ selectedProperty?.id }}</span>
          </p>
          <UFormField label="Novo Preço">
            <UInput 
              v-model="newPrice" 
              type="number" 
              icon="i-heroicons-currency-dollar" 
              size="lg"
            />
          </UFormField>
        </div>
      </template>
      <template #footer>
        <div class="flex justify-end gap-2 p-4">
          <UButton variant="ghost" color="neutral" @click="isPriceModalOpen = false">Cancelar</UButton>
          <UButton color="primary" :loading="isUpdatingPrice" @click="updatePrice">Salvar Alteração</UButton>
        </div>
      </template>
    </UModal>
  </UDashboardLayout>
</template>
