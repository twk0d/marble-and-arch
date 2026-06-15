import { defineStore } from 'pinia'
import { PropertyType, Amenity } from '~/types'

export const usePropertyFormStore = defineStore('propertyForm', {
  state: () => ({
    currentStep: 1,
    formData: {
      type: PropertyType.HOUSE,
      address: {
        zipCode: '',
        street: '',
        number: '',
        complement: '',
        neighborhood: '',
        city: '',
        state: ''
      },
      details: {
        bedrooms: 0,
        suites: 0,
        bathrooms: 0,
        parkingSpaces: 0,
        totalAreaValue: null as number | null,
        builtAreaValue: null as number | null,
        yearBuilt: null as number | null,
        description: ''
      },
      amenities: [] as Amenity[],
      images: [] as { url: string; description: string }[],
      price: {
        amount: null as number | null,
        currency: 'BRL',
        condominiumFee: null as number | null,
        iptu: null as number | null
      }
    }
  }),
  actions: {
    setStep(step: number) {
      this.currentStep = step
    },
    nextStep() {
      if (this.currentStep < 6) this.currentStep++
    },
    prevStep() {
      if (this.currentStep > 1) this.currentStep--
    },
    resetForm() {
      this.$reset()
    }
  },
  persist: true
})
