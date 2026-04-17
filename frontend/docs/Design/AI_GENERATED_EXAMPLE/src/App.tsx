import React, { useState, useEffect } from 'react';
import Navbar from './components/Navbar';
import PropertyCard from './components/PropertyCard';
import InteractiveFloorPlan from './components/InteractiveFloorPlan';
import ViewingScheduler from './components/ViewingScheduler';
import AiConcierge from './components/AiConcierge';
import Moodboard from './components/Moodboard';
import { INITIAL_PROPERTIES } from './data';
import { Property, ViewingBooking } from './types';
import { Search, Compass, ShieldCheck, Heart, Calendar, Landmark, Info, Sparkles, X, ChevronRight, CornerDownRight } from 'lucide-react';

export default function App() {
  // Global States
  const [activeTab, setActiveTab] = useState<'portfolio' | 'concierge' | 'moodboard' | 'bookings'>('portfolio');
  const [isDarkCanvas, setIsDarkCanvas] = useState<boolean>(false);
  const [properties, setProperties] = useState<Property[]>(INITIAL_PROPERTIES);
  const [savedIds, setSavedIds] = useState<Set<string>>(new Set(['estate-1', 'estate-2'])); // seed saved items
  const [bookings, setBookings] = useState<ViewingBooking[]>([]);
  const [searchQuery, setSearchQuery] = useState('');
  const [activeCategory, setActiveCategory] = useState<string>('All');

  // Detail Modal / Active Property state
  const [selectedProperty, setSelectedProperty] = useState<Property | null>(null);
  const [showBookingForm, setShowBookingForm] = useState<boolean>(false);

  // Sync bookings & custom curated designs on load
  const loadBookingsAndProperties = async () => {
    try {
      const bRes = await fetch('/api/bookings');
      if (bRes.ok) {
        const bData = await bRes.json();
        setBookings(bData.bookings || []);
      }

      const pRes = await fetch('/api/custom-properties');
      if (pRes.ok) {
        const pData = await pRes.json();
        if (pData.properties && pData.properties.length > 0) {
          // Merge custom AI-generated properties to state property list
          setProperties((prev) => {
            const staticIds = new Set(INITIAL_PROPERTIES.map((ip) => ip.id));
            const filteredCustom = pData.properties.filter((cp: Property) => !staticIds.has(cp.id));
            
            // Avoid duplicates
            const existingIds = new Set(prev.map(p => p.id));
            const newProperties = filteredCustom.filter((cp: Property) => !existingIds.has(cp.id));
            return [...prev, ...newProperties];
          });
        }
      }
    } catch (err) {
      console.warn("Server fetch simulated offline.", err);
    }
  };

  useEffect(() => {
    loadBookingsAndProperties();
  }, [activeTab]);

  const handlePropertyCuratedByAi = (newProperty: Property) => {
    setProperties((prev) => {
      if (prev.find((p) => p.id === newProperty.id)) return prev;
      return [newProperty, ...prev];
    });
    // Jump user to portfolio tab instantly to see it!
    setActiveTab('portfolio');
    setSelectedProperty(newProperty);
  };

  const handleSaveToggle = (propertyId: string, e: React.MouseEvent) => {
    e.stopPropagation();
    setSavedIds((prev) => {
      const next = new Set(prev);
      if (next.has(propertyId)) {
        next.delete(propertyId);
      } else {
        next.add(propertyId);
      }
      return next;
    });
  };

  const categories = ["All", "Waterfront", "Desert Oasis", "Mediterranean", "Historic", "Modern Minimalist"];

  // Filter conditions
  const filteredProperties = properties.filter((prop) => {
    const matchesSearch = 
      prop.title.toLowerCase().includes(searchQuery.toLowerCase()) || 
      prop.location.toLowerCase().includes(searchQuery.toLowerCase()) ||
      prop.specs.materials.some(m => m.toLowerCase().includes(searchQuery.toLowerCase()));

    const matchesCategory = 
      activeCategory === "All" || 
      prop.categories.includes(activeCategory);

    return matchesSearch && matchesCategory;
  });

  return (
    <div className={`min-h-screen transition-colors duration-500 pb-20 ${
      isDarkCanvas ? 'bg-[#121111] text-white' : 'bg-[#fcf9f8] text-[#1c1b1b]'
    }`}>
      {/* Absolute Master Navigation */}
      <Navbar 
        activeTab={activeTab} 
        setActiveTab={(tab) => {
          setActiveTab(tab);
          setSelectedProperty(null); // Clear selected property to avoid UI overlay
        }} 
        isDarkCanvas={isDarkCanvas} 
        setIsDarkCanvas={setIsDarkCanvas}
        bookingCount={bookings.length}
      />

      <div className="pt-28 max-w-7xl mx-auto px-6 md:px-12">
        {/* Render Tab Views */}
        {activeTab === 'portfolio' && (
          <div className="space-y-12">
            
            {/* HERO INTRODUCTION */}
            <div className="space-y-4 max-w-3xl">
              <span className={`text-[10px] tracking-[0.3em] font-bold uppercase ${isDarkCanvas ? 'text-[#ffdea5]' : 'text-[#775a19]'}`}>
                QUIET LUXURY &amp; DWELLING
              </span>
              <h2 className="font-serif text-3xl md:text-5xl lg:text-6xl font-bold tracking-tight leading-tight">
                Curated Architectural Discoveries
              </h2>
              <p className={`font-body-md text-sm md:text-base leading-relaxed ${isDarkCanvas ? 'text-surface-dim' : 'text-neutral-600'}`}>
                Welcome to a library of high-precision structures. Whether viewing our hand-built cliffside monoliths or directing the AI Studio to compile bespoke layouts, enjoy generous spacing, limestone finishes, and architectural serenity.
              </p>
            </div>

            {/* SELECTION AND FILTER ACTIONS */}
            <div className="flex flex-col md:flex-row justify-between items-start md:items-center gap-6 pb-6 border-b border-stone-500/10">
              
              {/* Filter Slots */}
              <div className="flex flex-wrap gap-2 text-xs font-semibold tracking-wider font-mono">
                {categories.map((cat) => (
                  <button
                    key={cat}
                    onClick={() => setActiveCategory(cat)}
                    className={`py-2 px-3.5 rounded transition-all duration-300 uppercase ${
                      activeCategory === cat
                        ? (isDarkCanvas ? 'bg-[#ffdea5] text-black font-extrabold' : 'bg-[#775a19] text-white font-extrabold')
                        : (isDarkCanvas ? 'bg-[#212020] text-stone-300 border border-[#ca9e5a]/10 hover:bg-stone-800' : 'bg-white border text-[#645d53] hover:bg-stone-50')
                    }`}
                  >
                    {cat}
                  </button>
                ))}
              </div>

              {/* Minimal Line Search Bar */}
              <div className="relative w-full md:w-80">
                <input
                  type="text"
                  placeholder="Query Materials, Slabs, or Locations..."
                  value={searchQuery}
                  onChange={(e) => setSearchQuery(e.target.value)}
                  className={`w-full py-2.5 pl-10 pr-4 bg-transparent outline-none text-xs border-b-2 transition-colors duration-300 font-medium ${
                    isDarkCanvas 
                      ? 'border-[#ca9e5a]/20 text-white focus:border-[#ffdea5]' 
                      : 'border-secondary-container text-neutral-800 focus:border-[#775a19]'
                  }`}
                />
                <Search className="w-4 h-4 text-stone-400 absolute left-2 top-3" strokeWidth={1.5} />
              </div>

            </div>

            {/* ACTIVE PORTFOLIO GRID */}
            {filteredProperties.length === 0 ? (
              <div className="text-center py-20 border-2 border-dashed border-stone-500/10 rounded-lg">
                <Info className="w-10 h-10 mx-auto text-stone-400 mb-2" strokeWidth={1.5} />
                <h4 className="font-serif text-lg font-bold">No structural profiles matched your criteria</h4>
                <p className="text-xs text-stone-500 mt-1">Try broadening your search text or categorizations.</p>
              </div>
            ) : (
              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
                {filteredProperties.map((prop) => (
                  <PropertyCard 
                    key={prop.id}
                    property={prop}
                    isSaved={savedIds.has(prop.id)}
                    isDarkCanvas={isDarkCanvas}
                    onSaveToggle={(e) => handleSaveToggle(prop.id, e)}
                    onSelect={() => setSelectedProperty(prop)}
                    onRequestBooking={(e) => {
                      e.stopPropagation();
                      setSelectedProperty(prop);
                      setShowBookingForm(true);
                    }}
                  />
                ))}
              </div>
            )}
          </div>
        )}

        {activeTab === 'concierge' && (
          <AiConcierge 
            isDarkCanvas={isDarkCanvas}
            onPropertyCurated={handlePropertyCuratedByAi}
          />
        )}

        {activeTab === 'moodboard' && (
          <Moodboard 
            savedProperties={properties.filter(p => savedIds.has(p.id))}
            onRemoveSave={(id) => {
              setSavedIds(prev => {
                const next = new Set(prev);
                next.delete(id);
                return next;
              });
            }}
            isDarkCanvas={isDarkCanvas}
          />
        )}

        {activeTab === 'bookings' && (
          <div className="space-y-8">
            <div>
              <span className={`text-[10px] tracking-widest uppercase font-bold ${isDarkCanvas ? 'text-[#ffdea5]' : 'text-[#775a19]'}`}>
                CLIENT INTERACTION DICTIONARY
              </span>
              <h2 className="font-serif text-2xl md:text-3xl font-bold mt-0.5">
                Private Viewing Records
              </h2>
              <p className={`text-xs mt-1.5 ${isDarkCanvas ? 'text-surface-dim' : 'text-[#645d53]'}`}>
                Your active requests with our private client partners and helicopter/access coordination logs.
              </p>
            </div>

            {bookings.length === 0 ? (
              <div className="p-16 text-center border rounded border-dashed">
                <Calendar className="w-12 h-12 text-stone-300 mx-auto mb-4" strokeWidth={1} />
                <h4 className="font-serif text-lg font-bold">No Private Tours Booked</h4>
                <p className="text-xs text-stone-500 max-w-sm mx-auto mt-1 leading-relaxed">
                  Go to our portfolio showcase catalog, pick any estate that catches your interest, and schedule a personalized, accompanied onsite visitation.
                </p>
              </div>
            ) : (
              <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                {bookings.map((booking) => {
                  const correlatedProperty = properties.find(p => p.id === booking.propertyId);
                  return (
                    <div 
                      key={booking.id}
                      className={`p-6 rounded-lg border flex flex-col justify-between select-none ${
                        isDarkCanvas ? 'bg-[#1e1e1e] border-[#ca9e5a]/10' : 'bg-white border-secondary-container/40'
                      }`}
                    >
                      <div className="space-y-4">
                        <div className="flex justify-between items-start">
                          <div>
                            <span className="text-[8px] font-semibold tracking-widest font-mono text-stone-400 block">TOUR ID // {booking.id.toUpperCase()}</span>
                            <h4 className="font-serif text-base font-bold mt-1">
                              {booking.propertyName}
                            </h4>
                          </div>
                          <span className={`px-2.5 py-0.5 rounded-full text-[8px] tracking-wider uppercase font-semibold border ${
                            isDarkCanvas ? 'bg-[#ffdea5]/10 border-[#ffdea5]/20 text-[#ffdea5]' : 'bg-[#775a19]/10 border-[#775a19]/20 text-[#775a19]'
                          }`}>
                            Confirmed Log
                          </span>
                        </div>

                        <div className={`p-4 rounded border grid grid-cols-2 gap-4 text-xs ${isDarkCanvas ? 'bg-stone-800 border-stone-700/50' : 'bg-[#fcf9f8] border-stone-200/50'}`}>
                          <div>
                            <span className="text-[8px] font-semibold uppercase tracking-wider text-stone-400">Date</span>
                            <p className="font-medium mt-0.5">{booking.date}</p>
                          </div>
                          <div>
                            <span className="text-[8px] font-semibold uppercase tracking-wider text-stone-400">Time Segment</span>
                            <p className="font-mono font-medium mt-0.5">{booking.time}</p>
                          </div>
                          <div className="col-span-2 border-t pt-2 mt-1">
                            <span className="text-[8px] font-semibold uppercase tracking-wider text-stone-400 block">Dossier Contact</span>
                            <p className="font-medium mt-0.5">{booking.clientName} ({booking.clientEmail})</p>
                          </div>
                        </div>

                        {booking.notes && (
                          <div className="text-xs">
                            <span className="text-[8.5px] uppercase font-bold text-[#c5a059] block mb-1">Dossier Notes</span>
                            <p className="italic leading-relaxed text-stone-400">"{booking.notes}"</p>
                          </div>
                        )}
                      </div>

                      {correlatedProperty && (
                        <div className="mt-6 pt-4 border-t flex gap-3 items-center">
                          <button
                            onClick={() => {
                              setSelectedProperty(correlatedProperty);
                              setActiveTab('portfolio');
                            }}
                            className="text-[9px] uppercase tracking-widest font-bold text-[#c5a059] hover:underline"
                          >
                            Explore Architectural Layout →
                          </button>
                        </div>
                      )}
                    </div>
                  );
                })}
              </div>
            )}
          </div>
        )}
      </div>

      {/* IMMERSIVE PROPERTY DETAILS FOCUS VIEW (Fullscreen Modal overlay) */}
      {selectedProperty && (
        <div className="fixed inset-0 z-50 overflow-y-auto bg-black/85 backdrop-blur-md flex items-center justify-center p-4 md:p-8 animate-fade-in">
          <div className={`relative max-w-5xl w-full rounded-lg overflow-hidden shadow-2xl ${
            isDarkCanvas ? 'bg-[#181717] border border-stone-800' : 'bg-[#fcf9f8] border border-stone-200/60'
          }`}>
            
            {/* Close Modal button */}
            <button 
              onClick={() => {
                setSelectedProperty(null);
                setShowBookingForm(false);
              }}
              className="absolute top-4 right-4 z-10 p-2.5 rounded-full bg-black/50 hover:bg-black/80 text-white/90 cursor-pointer transition-all border border-white/10"
              title="Close Panel"
            >
              <X strokeWidth={1.5} className="w-4 h-4" />
            </button>

            {/* Immersive Photo Hero banner */}
            <div className="relative h-64 md:h-96 w-full bg-stone-100">
              <img 
                src={selectedProperty.imageUrl} 
                alt={selectedProperty.title}
                referrerPolicy="no-referrer"
                className="w-full h-full object-cover"
              />
              <div className="absolute inset-0 bg-gradient-to-t from-black/80 via-black/10 to-transparent" />
              
              <div className="absolute bottom-6 left-6 right-6 text-white space-y-1">
                <span className="text-[10px] tracking-widest uppercase font-mono bg-black/30 py-1 px-3.5 rounded-full border border-white/10">
                  {selectedProperty.location}
                </span>
                <h2 className="font-serif text-3xl md:text-5xl font-bold tracking-tight mt-1">
                  {selectedProperty.title}
                </h2>
              </div>
            </div>

            {/* Content Tabs block */}
            <div className="grid grid-cols-1 lg:grid-cols-12 gap-8 p-6 md:p-8">
              
              {/* Left Column: Descriptions, Materials, and Tour booking switches */}
              <div className="lg:col-span-7 space-y-6">
                <div>
                  <span className={`text-[9px] tracking-widest uppercase font-mono ${isDarkCanvas ? 'text-[#ffdea5]' : 'text-[#775a19]'}`}>
                    Editorial Review
                  </span>
                  
                  {selectedProperty.editorialQuote && (
                    <blockquote className="font-serif text-lg md:text-xl font-medium tracking-wide italic text-stone-500 mt-1 mb-3">
                      "{selectedProperty.editorialQuote}"
                    </blockquote>
                  )}
                  
                  <p className={`text-xs md:text-sm leading-relaxed ${isDarkCanvas ? 'text-surface-dim' : 'text-neutral-600'}`}>
                    {selectedProperty.description}
                  </p>
                </div>

                <div className="grid grid-cols-2 gap-4 border-t border-b border-stone-500/10 py-6">
                  <div>
                    <span className="text-[9.5px] uppercase tracking-widest text-[#c5a059] block mb-2 font-semibold">
                      Design Slabs &amp; Masonry
                    </span>
                    <ul className={`space-y-1 text-xs ${isDarkCanvas ? 'text-stone-300' : 'text-neutral-700'}`}>
                      {selectedProperty.specs.materials.map((m) => (
                        <li key={m} className="flex items-center gap-1.5 font-medium">
                          <CornerDownRight strokeWidth={1.5} className="w-3 h-3 text-stone-400" />
                          {m}
                        </li>
                      ))}
                    </ul>
                  </div>

                  <div>
                    <span className="text-[9.5px] uppercase tracking-widest text-[#c5a059] block mb-2 font-semibold">
                      Highlights &amp; Features
                    </span>
                    <ul className={`space-y-1 text-xs ${isDarkCanvas ? 'text-stone-300' : 'text-neutral-700'}`}>
                      {selectedProperty.features.slice(0, 4).map((f) => (
                        <li key={f} className="flex items-center gap-1.5 font-medium line-clamp-1">
                          <CornerDownRight strokeWidth={1.5} className="w-3 h-3 text-stone-400" />
                          {f}
                        </li>
                      ))}
                    </ul>
                  </div>
                </div>

                {/* Private hosting agent */}
                <div className={`p-4 rounded border flex items-center gap-4 justify-between ${
                  isDarkCanvas ? 'bg-[#212020] border-neutral-800' : 'bg-[#fcf9f8] border-[#e8ded1]/30'
                }`}>
                  <div className="flex items-center gap-3">
                    <img 
                      src={selectedProperty.agent.avatarUrl} 
                      alt={selectedProperty.agent.name} 
                      referrerPolicy="no-referrer"
                      className="w-11 h-11 rounded-full object-cover border border-[#c5a059]"
                    />
                    <div>
                      <h5 className="font-serif text-sm font-semibold">{selectedProperty.agent.name}</h5>
                      <p className="text-[10px] text-stone-400">{selectedProperty.agent.role}</p>
                    </div>
                  </div>
                  <button
                    onClick={() => setShowBookingForm(!showBookingForm)}
                    className={`py-2 px-4 shadow border rounded text-[8.5px] font-bold tracking-widest uppercase cursor-pointer transition-all ${
                      showBookingForm
                        ? (isDarkCanvas ? 'bg-stone-800 border-stone-700 text-[#ffdea5]' : 'bg-[#fcf9f8] border-stone-300 text-neutral-600')
                        : (isDarkCanvas ? 'bg-[#ffdea5] text-black border-transparent' : 'bg-[#775a19] text-white border-transparent')
                    }`}
                  >
                    {showBookingForm ? "Explore Blueprint" : "Book Touring Slots"}
                  </button>
                </div>
              </div>

              {/* Right Column: Floor plan visualizer or the date scheduler */}
              <div className="lg:col-span-5">
                {showBookingForm ? (
                  <ViewingScheduler 
                    property={selectedProperty} 
                    isDarkCanvas={isDarkCanvas}
                    onSuccess={() => {
                      loadBookingsAndProperties();
                      // Close modals after brief interval
                      setTimeout(() => {
                        setSelectedProperty(null);
                        setShowBookingForm(false);
                      }, 2500);
                    }}
                  />
                ) : (
                  <InteractiveFloorPlan 
                    property={selectedProperty}
                    isDarkCanvas={isDarkCanvas}
                  />
                )}
              </div>

            </div>
          </div>
        </div>
      )}

      {/* Decorative footer statement */}
      <footer className="mt-20 py-8 border-t border-stone-500/10 text-center text-[10px] text-stone-400 uppercase tracking-widest leading-relaxed">
        <p>© 2026 Marble &amp; Arch Estate Studio. All rights reserved.</p>
        <p className="opacity-70 mt-1">Refined Spatial Design &amp; Architectural Planning // San Francisco // Amalfi // Tokyo</p>
      </footer>
    </div>
  );
}
