export interface PropertyAgent {
  name: string;
  role: string;
  avatarUrl: string;
  phone: string;
}

export interface PropertySpecs {
  beds: number;
  baths: number;
  sqft: number;
  yearBuilt: number;
  materials: string[];
}

export interface Property {
  id: string;
  title: string;
  location: string;
  price: string;
  rawPrice: number; // For sorting
  imageUrl: string;
  description: string;
  editorialQuote?: string;
  categories: string[]; // e.g., 'Waterfront', 'Penthouse', 'Desert Oasis', 'Historic'
  specs: PropertySpecs;
  features: string[];
  floorPlanKeyHighlights: string[];
  floorPlanRooms: {
    name: string;
    description: string;
    dimensions: string;
    x: number; // SVG center coordinate
    y: number; // SVG center coordinate
    width: number;
    height: number;
  }[];
  agent: PropertyAgent;
  isAiGenerated?: boolean;
}

export interface ViewingBooking {
  id: string;
  propertyId: string;
  propertyName: string;
  clientName: string;
  clientEmail: string;
  date: string;
  time: string;
  notes?: string;
  createdAt: string;
}

export interface MoodboardComment {
  id: string;
  text: string;
  createdAt: string;
}

export interface MoodboardItem {
  propertyId: string;
  savedAt: string;
  designerNotes?: string;
  comments: MoodboardComment[];
}

export interface Message {
  id: string;
  sender: 'user' | 'concierge';
  text: string;
  timestamp: string;
  suggestedProperties?: Property[];
}
