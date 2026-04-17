import type { AddressData, AddressLookupAdapter } from './AddressLookupAdapter';

export class ViaCepAdapter implements AddressLookupAdapter {
  async lookup(cep: string): Promise<AddressData | null> {
    const cleanCep = cep.replace(/\D/g, '');
    if (cleanCep.length !== 8) {
      return null;
    }

    try {
      const response = await fetch(`https://viacep.com.br/ws/${cleanCep}/json/`);
      if (!response.ok) {
        return null;
      }
      
      const data = await response.json();
      
      // ViaCEP returns { erro: true } if the CEP doesn't exist
      if (data.erro) {
        return null;
      }

      return {
        street: data.logradouro || '',
        neighborhood: data.bairro || '',
        city: data.localidade || '',
        state: data.uf || ''
      };
    } catch (error) {
      console.error('ViaCEP lookup failed:', error);
      return null;
    }
  }
}
