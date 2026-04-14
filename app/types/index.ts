export enum PropertyType {
    CONDOMINIUM_HOUSE = 'CONDOMINIUM_HOUSE',
    CONDOMINIUM_PLOT = 'CONDOMINIUM_PLOT',
    COUNTRY_HOUSE = 'COUNTRY_HOUSE',
    HOUSE = 'HOUSE',
    OFFICE = 'OFFICE',
    OTHERS = 'OTHERS',
    PENTHOUSE = 'PENTHOUSE',
    PLOT = 'PLOT',
    STUDIO = 'STUDIO',
    WAREHOUSE = 'WAREHOUSE'
}

export enum Amenity {
    POOL = 'POOL',
    GYM = 'GYM',
    PARTY_ROOM = 'PARTY_ROOM',
    GOURMET_AREA = 'GOURMET_AREA',
    SAUNA = 'SAUNA',
    SPORTS_COURT = 'SPORTS_COURT',
    PLAYGROUND = 'PLAYGROUND',
    GARDEN = 'GARDEN',
    CONCIERGE_24H = 'CONCIERGE_24H'
}

export interface PageableFilters {
    type?: PropertyType;
    minPrice?: number;
    maxPrice?: number;
    minBedrooms?: number;
    minSuites?: number;
    minParkingSpaces?: number;
    city?: string;
    state?: string;
    amenities?: Amenity[];
}

export interface PropertySummaryDTO {
    id: string;
    type: PropertyType;
    city: string;
    state: string;
    priceAmount: number;
    priceCurrency: string;
    mainImageUrl: string | null;
    active: boolean;
}

export interface AdminDashboardDTO {
    totalProperties: number;
    activeProperties: number;
    inactiveProperties: number;
    totalMarketValue: number;
    distributionByType: Record<PropertyType, number>;
    distributionByCity: Record<string, number>;
    recentActivitiesCount: number;
}

export interface Address {
    street: string;
    number: string;
    complement: string | null;
    neighborhood: string;
    city: string;
    state: string;
    zipCode: string;
}

export interface Money {
    amount: number;
    currency: string;
}

export interface ImageDTO {
    id: string;
    url: string;
    description: string;
    active: boolean;
}

export interface PropertyDTO {
    id: string;
    active: boolean;
    images: ImageDTO[];
    type: PropertyType;
    address: Address;
    price: Money;
    details: any;
}

export interface CreatePropertyRequest {
    propertyType: PropertyType;
    street: string;
    number: string;
    neighborhood: string;
    city: string;
    state: string;
    postalCode: string;
    complement?: string;
    priceAmount: number;
    priceCurrency: string;
    details: any;
}

export interface HouseDetailsRequest {
    bedrooms: number;
    suites: number;
    bathrooms: number;
    parkingSpaces: number;
    totalAreaValue: number;
    totalAreaUnit: 'SQUARE_METERS';
    builtAreaValue: number;
    builtAreaUnit: 'SQUARE_METERS';
    yearBuilt: number;
    description: string;
    hasGarage: boolean;
    hasPool: boolean;
    hasBalcony: boolean;
}

export interface User {
    email: string;
    name?: string;
    role?: string;
}

export interface AuthState {
    token: string | null;
    user: User | null;
}

export interface LeadDTO {
    id: string;
    propertyId: string;
    customerName: string;
    customerEmail: string;
    customerPhone?: string;
    message?: string;
    createdAt: string;
    status: 'NEW' | 'CONTACTED' | 'ARCHIVED';
}
