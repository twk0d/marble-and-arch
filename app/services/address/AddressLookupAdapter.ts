export interface AddressData {
  street: string;
  neighborhood: string;
  city: string;
  state: string;
}

export interface AddressLookupAdapter {
  lookup(cep: string): Promise<AddressData | null>;
}
