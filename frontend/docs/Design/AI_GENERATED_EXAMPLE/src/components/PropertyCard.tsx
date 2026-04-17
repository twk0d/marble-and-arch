import React from 'react';
import { Heart, Maximize2, Bed, Bath, ArrowUpRight, Calendar, Sparkles } from 'lucide-react';
import { Property } from '../types';

interface PropertyCardProps {
  key?: string | number;
  property: Property;
  isSaved: boolean;
  onSaveToggle: (e: React.MouseEvent) => void;
  onSelect: () => void;
  onRequestBooking: (e: React.MouseEvent) => void;
  isDarkCanvas: boolean;
}

export default function PropertyCard({
  property,
  isSaved,
  onSaveToggle,
  onSelect,
  onRequestBooking,
  isDarkCanvas
}: PropertyCardProps) {
  const { title, location, price, imageUrl, specs, categories, isAiGenerated } = property;

  return (
    <div 
      onClick={onSelect}
      className={`group relative rounded-lg overflow-hidden transition-all duration-700 cursor-pointer ${
        isDarkCanvas 
          ? 'bg-[#1e1e1e] hover:bg-[#252424] border border-[#4e4639]/20' 
          : 'bg-white hover:bg-[#fbf9f8] border border-[#e8ded1]/40'
      } shadow-sm hover:shadow-[0_32px_64px_-12px_rgba(119,90,25,0.08)]`}
    >
      {/* Property Image Cover */}
      <div className="relative aspect-[4/3] overflow-hidden bg-stone-100">
        <img 
          src={imageUrl} 
          alt={title}
          referrerPolicy="no-referrer"
          className="w-full h-full object-cover transition-transform duration-[1200ms] ease-out group-hover:scale-105"
        />
        
        {/* Transparent dark gradient for contrast */}
        <div className="absolute inset-0 bg-gradient-to-t from-black/50 via-transparent to-transparent opacity-85" />

        {/* Floating Category Badges & Save action */}
        <div className="absolute top-4 left-4 right-4 flex justify-between items-start gap-2">
          <div className="flex flex-wrap gap-1.5 max-w-[80%]">
            {isAiGenerated && (
              <span className="flex items-center gap-1.5 px-2.5 py-1 text-[9px] font-semibold tracking-wider uppercase text-white bg-[#775a19]/90 backdrop-blur-md rounded-sm border border-[#ffdea5]/30">
                <Sparkles strokeWidth={2} className="w-2.5 h-2.5 text-[#ffdea5]" />
                Bespoke Design
              </span>
            )}
            {categories.slice(0, 2).map((cat) => (
              <span 
                key={cat}
                className="px-2.5 py-1 text-[9px] font-semibold tracking-wider uppercase text-white bg-black/40 backdrop-blur-md rounded-sm border border-white/20"
              >
                {cat}
              </span>
            ))}
          </div>

          <button
            onClick={onSaveToggle}
            className={`p-2 rounded-full backdrop-blur-md border cursor-pointer transition-all duration-300 ${
              isSaved 
                ? 'bg-[#775a19] border-[#ffdea5]/40 text-white' 
                : 'bg-black/35 border-white/20 text-white/90 hover:bg-[#775a19]/90 hover:border-white/40'
            }`}
            title={isSaved ? "Saved to Moodboard" : "Save to Designer Moodboard"}
          >
            <Heart strokeWidth={1.5} className={`w-3.5 h-3.5 ${isSaved ? 'fill-current' : ''}`} />
          </button>
        </div>

        {/* Location layout overlapping the image lower boundary */}
        <div className="absolute bottom-4 left-4 right-4 text-white">
          <span className="font-label-sm text-[9px] opacity-90 tracking-widest uppercase">
            {location}
          </span>
          <h3 className="font-serif text-lg md:text-xl font-bold tracking-tight leading-tight mt-1">
            {title}
          </h3>
        </div>
      </div>

      {/* Property Details Sheet */}
      <div className="p-5">
        <div className="flex justify-between items-baseline mb-4">
          <div className="flex flex-col">
            <span className={`text-[9px] tracking-widest uppercase font-semibold ${isDarkCanvas ? 'text-[#ffdea5]' : 'text-[#775a19]'}`}>
              Investment Value
            </span>
            <span className={`font-serif text-xl sm:text-2xl font-semibold mt-0.5 ${isDarkCanvas ? 'text-white' : 'text-neutral-900'}`}>
              {price}
            </span>
          </div>
          
          <span className={`flex items-center gap-1 text-[10px] font-semibold tracking-widest uppercase group-hover:translate-x-1 group-hover:-translate-y-0.5 transition-transform duration-300 ${isDarkCanvas ? 'text-[#ffdea5]' : 'text-[#775a19]'}`}>
            Explore Studio
            <ArrowUpRight className="w-3.5 h-3.5" />
          </span>
        </div>

        <p className={`text-xs line-clamp-2 leading-relaxed mb-4 ${isDarkCanvas ? 'text-surface-dim' : 'text-neutral-600'}`}>
          {property.description}
        </p>

        {/* Architectural Specifications Grid */}
        <div className={`grid grid-cols-3 gap-2 py-4 border-t border-b ${isDarkCanvas ? 'border-[#4e4639]/20' : 'border-[#e8ded1]/30'} mb-4`}>
          <div className="flex flex-col items-center justify-center p-1.5 rounded bg-stone-500/5">
            <Bed strokeWidth={1} className={`w-4 h-4 mb-1 ${isDarkCanvas ? 'text-[#ffdea5]' : 'text-[#775a19]'}`} />
            <span className={`text-[11px] font-semibold ${isDarkCanvas ? 'text-white' : 'text-neutral-800'}`}>
              {specs.beds} Beds
            </span>
          </div>
          <div className="flex flex-col items-center justify-center p-1.5 rounded bg-stone-500/5">
            <Bath strokeWidth={1} className={`w-4 h-4 mb-1 ${isDarkCanvas ? 'text-[#ffdea5]' : 'text-[#775a19]'}`} />
            <span className={`text-[11px] font-semibold ${isDarkCanvas ? 'text-white' : 'text-neutral-800'}`}>
              {specs.baths} Baths
            </span>
          </div>
          <div className="flex flex-col items-center justify-center p-1.5 rounded bg-stone-500/5">
            <Maximize2 strokeWidth={1} className={`w-4 h-4 mb-1 ${isDarkCanvas ? 'text-[#ffdea5]' : 'text-[#775a19]'}`} />
            <span className={`text-[11px] font-semibold ${isDarkCanvas ? 'text-white' : 'text-neutral-800'}`}>
              {specs.sqft.toLocaleString()} Sqft
            </span>
          </div>
        </div>

        {/* Action Button: Book viewing */}
        <button
          onClick={onRequestBooking}
          className={`w-full py-2.5 rounded text-center text-[10px] font-semibold tracking-widest uppercase cursor-pointer border transition-all duration-300 flex items-center justify-center gap-1.5 ${
            isDarkCanvas 
              ? 'bg-[#2a2929] border-[#4e4639]/30 text-white hover:bg-[#ffdea5] hover:text-black hover:border-transparent' 
              : 'bg-[#fcf9f8] border-[#e8ded1] text-secondary-oncontainer hover:bg-[#775a19] hover:text-white hover:border-transparent'
          }`}
        >
          <Calendar strokeWidth={1.5} className="w-3.5 h-3.5" />
          Request Private Tour
        </button>
      </div>
    </div>
  );
}
