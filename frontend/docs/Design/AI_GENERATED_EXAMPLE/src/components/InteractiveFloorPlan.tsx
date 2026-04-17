import { useState } from 'react';
import { Property } from '../types';
import { Compass, Sparkles, Move, Split, Layers } from 'lucide-react';

interface InteractiveFloorPlanProps {
  property: Property;
  isDarkCanvas: boolean;
}

export default function InteractiveFloorPlan({ property, isDarkCanvas }: InteractiveFloorPlanProps) {
  const [selectedRoomIndex, setSelectedRoomIndex] = useState<number>(0);

  const rooms = property.floorPlanRooms || [];
  const selectedRoom = rooms[selectedRoomIndex] || rooms[0];

  return (
    <div className={`p-6 rounded-lg ${isDarkCanvas ? 'bg-[#1e1e1e] border border-[#4e4639]/20' : 'bg-white border border-[#e8ded1]/30'} shadow-md`}>
      <div className="flex flex-col md:flex-row justify-between items-start md:items-center gap-4 mb-6 pb-4 border-b border-stone-500/10">
        <div>
          <span className={`text-[9px] tracking-widest uppercase font-semibold flex items-center gap-1 ${isDarkCanvas ? 'text-[#ffdea5]' : 'text-[#775a19]'}`}>
            <Compass strokeWidth={2} className="w-2.5 h-2.5" />
            Interactive Spatial Studio
          </span>
          <h3 className={`font-serif text-xl font-bold mt-1 ${isDarkCanvas ? 'text-white' : 'text-neutral-900'}`}>
            Master Layout &amp; Flow
          </h3>
        </div>
        <div className={`flex flex-wrap gap-2 text-[10px] uppercase font-semibold tracking-wider p-1.5 rounded ${isDarkCanvas ? 'bg-stone-800' : 'bg-[#fcf9f8]'}`}>
          <span className="text-[#c5a059]">Interactive Blueprint Grid</span>
        </div>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-5 gap-8">
        {/* Interactive SVG Blueprint Container */}
        <div className="lg:col-span-3 flex flex-col justify-between">
          <div className="relative aspect-[4/3] w-full rounded border border-dashed border-stone-300/40 p-1 bg-stone-500/5 overflow-hidden flex items-center justify-center">
            
            {/* Architectural Blueprint Grid Pattern overlay */}
            <div className="absolute inset-0 pointer-events-none opacity-[0.06] grid grid-cols-10 grid-rows-10">
              {Array.from({ length: 100 }).map((_, i) => (
                <div key={i} className="border-[0.5px] border-stone-500" />
              ))}
            </div>

            {/* The CAD / SVG rendering canvas */}
            <svg 
              viewBox="0 0 100 100" 
              className="w-full h-full max-h-[380px] drop-shadow-md select-none transition-all"
            >
              {/* Outer boundary guidelines */}
              <rect x="2" y="2" width="96" height="96" fill="none" stroke={isDarkCanvas ? "rgba(197, 160, 89, 0.15)" : "rgba(119, 90, 25, 0.1)"} strokeWidth="0.5" strokeDasharray="3 3" />
              
              {/* Center axis guide mark */}
              <circle cx="50" cy="50" r="1.5" fill={isDarkCanvas ? "#ffa500" : "#775a19"} opacity="0.3" />
              <line x1="50" y1="5" x2="50" y2="95" stroke={isDarkCanvas ? "#ffdea5" : "#775a19"} opacity="0.1" strokeWidth="0.2" />
              <line x1="5" y1="50" x2="95" y2="50" stroke={isDarkCanvas ? "#ffdea5" : "#775a19"} opacity="0.1" strokeWidth="0.2" />

              {/* Dynamically generated room footprints */}
              {rooms.map((room, idx) => {
                const isSelected = selectedRoomIndex === idx;
                
                // SVG coordinates mapped from room object center (x, y) & bounds (width, height)
                const rx = room.x - room.width / 2;
                const ry = room.y - room.height / 2;

                return (
                  <g 
                    key={room.name}
                    className="cursor-pointer group"
                    onClick={() => setSelectedRoomIndex(idx)}
                  >
                    {/* Room Footprint Rectangle */}
                    <rect
                      x={rx}
                      y={ry}
                      width={room.width}
                      height={room.height}
                      rx="1"
                      className="transition-all duration-500"
                      fill={
                        isSelected
                          ? (isDarkCanvas ? 'rgba(197, 160, 89, 0.25)' : 'rgba(119, 90, 25, 0.15)')
                          : (isDarkCanvas ? 'rgba(255, 255, 255, 0.03)' : 'rgba(119, 90, 25, 0.02)')
                      }
                      stroke={
                        isSelected
                          ? (isDarkCanvas ? '#ffdea5' : '#775a19')
                          : (isDarkCanvas ? 'rgba(255,255,255,0.2)' : 'rgba(119,90,25,0.25)')
                      }
                      strokeWidth={isSelected ? "1" : "0.5"}
                    />

                    {/* Tiny dimension marking line overlay on hover */}
                    {isSelected && (
                      <g className="opacity-70 animate-pulse">
                        {/* Width indicator */}
                        <line x1={rx} y1={ry - 2} x2={rx + room.width} y2={ry - 2} stroke={isDarkCanvas ? "#ffdea5" : "#775a19"} strokeWidth="0.3" />
                        <circle cx={rx} cy={ry - 2} r="0.5" fill={isDarkCanvas ? "#ffdea5" : "#775a19"} />
                        <circle cx={rx + room.width} cy={ry - 2} r="0.5" fill={isDarkCanvas ? "#ffdea5" : "#775a19"} />
                        
                        {/* Height indicator */}
                        <line x1={rx - 2} y1={ry} x2={rx - 2} y2={ry + room.height} stroke={isDarkCanvas ? "#ffdea5" : "#775a19"} strokeWidth="0.3" />
                        <circle cx={rx - 2} cy={ry} r="0.5" fill={isDarkCanvas ? "#ffdea5" : "#775a19"} />
                        <circle cx={rx - 2} cy={ry + room.height} r="0.5" fill={isDarkCanvas ? "#ffdea5" : "#775a19"} />
                      </g>
                    )}

                    {/* Room Text Label on blueprint */}
                    <text
                      x={room.x}
                      y={room.y}
                      textAnchor="middle"
                      dominantBaseline="middle"
                      className="transition-colors duration-300 font-serif select-none"
                      fontSize={room.width < 18 ? "2.5" : "3.2"}
                      fontWeight="bold"
                      fill={
                        isSelected
                          ? (isDarkCanvas ? '#ffdea5' : '#775a19')
                          : (isDarkCanvas ? '#eae7e7' : '#645d53')
                      }
                    >
                      {room.name.split(' ')[0]} {/* First word to avoid overcrowding */}
                    </text>
                    
                    {/* Dimension specifications printed below center */}
                    <text
                      x={room.x}
                      y={room.y + 4}
                      textAnchor="middle"
                      className="opacity-50 select-none font-mono text-[1.8px] sm:text-[2px]"
                      fill={isDarkCanvas ? '#ffffff' : '#1c1b1b'}
                    >
                      {room.dimensions}
                    </text>
                  </g>
                );
              })}
            </svg>
          </div>

          <div className="mt-4 flex gap-4 items-center p-3 rounded bg-stone-500/5">
            <Move strokeWidth={1.5} className={`w-4 h-4 ${isDarkCanvas ? 'text-[#ffdea5]' : 'text-[#775a19]'}`} />
            <p className={`text-[11px] leading-relaxed ${isDarkCanvas ? 'text-surface-dim' : 'text-neutral-600'}`}>
              <strong>Interactive Interaction:</strong> Click any structural chamber within the blueprint layout to inspect dimensions, design philosophy, and curated spatial materials.
            </p>
          </div>
        </div>

        {/* Selected Room Specifications Details Panel */}
        <div className="lg:col-span-2 flex flex-col justify-between">
          {selectedRoom ? (
            <div className="flex flex-col justify-between h-full gap-6">
              {/* Active description box */}
              <div className={`p-5 rounded border ${isDarkCanvas ? 'bg-[#2a2929] border-[#4e4639]/30' : 'bg-[#fffdfd] border-[#e8ded1]/50'} flex-grow`}>
                <span className={`font-mono text-[9px] uppercase tracking-wider ${isDarkCanvas ? 'text-[#ffdea5]' : 'text-[#c5a059]'}`}>
                  Active Chamber {selectedRoomIndex + 1} of {rooms.length}
                </span>

                <h4 className={`font-serif text-lg font-bold mt-1 ${isDarkCanvas ? 'text-white' : 'text-neutral-900'}`}>
                  {selectedRoom.name}
                </h4>

                <div className={`inline-block px-2.5 py-1 text-[10px] font-semibold tracking-wider font-mono rounded my-3 ${isDarkCanvas ? 'bg-stone-800 text-white' : 'bg-stone-100 text-neutral-800'}`}>
                  Span: {selectedRoom.dimensions}
                </div>

                <p className={`text-xs leading-relaxed mt-2 ${isDarkCanvas ? 'text-surface-dim' : 'text-neutral-600'}`}>
                  {selectedRoom.description}
                </p>

                {/* Simulated materials mapping matches the design system sheet */}
                <div className="mt-6 border-t border-stone-500/10 pt-4">
                  <span className={`text-[9px] uppercase tracking-widest font-semibold block mb-2 ${isDarkCanvas ? 'text-[#ffdea5]' : 'text-[#775a19]'}`}>
                    Aesthetic Palette Materials
                  </span>
                  <div className="flex flex-wrap gap-1.5">
                    {(property.specs.materials || ["Travertine", "Concrete", "Wood"]).slice(0, 3).map((material) => (
                      <span 
                        key={material}
                        className={`text-[9px] font-semibold tracking-wide uppercase px-2.5 py-1 border rounded-sm ${
                          isDarkCanvas 
                            ? 'bg-[#1c1b1b] border-[#4e4639]/50 text-stone-300' 
                            : 'bg-white border-[#e8ded1] text-[#645d53]'
                        }`}
                      >
                        {material}
                      </span>
                    ))}
                  </div>
                </div>
              </div>

              {/* Selector buttons list */}
              <div className="space-y-1.5">
                <span className={`text-[9px] uppercase tracking-widest font-semibold block ${isDarkCanvas ? 'text-stone-300' : 'text-neutral-600'} mb-1`}>
                  Room Directory
                </span>
                <div className="max-h-[143px] overflow-y-auto space-y-1 pr-1">
                  {rooms.map((room, idx) => (
                    <button
                      key={room.name}
                      onClick={() => setSelectedRoomIndex(idx)}
                      className={`w-full p-2.5 text-left text-xs rounded transition-all duration-300 flex items-center justify-between cursor-pointer ${
                        selectedRoomIndex === idx
                          ? (isDarkCanvas ? 'bg-[#ffdea5] text-black font-semibold' : 'bg-[#775a19] text-white font-semibold')
                          : (isDarkCanvas ? 'bg-neutral-800/50 hover:bg-neutral-800 text-stone-300' : 'bg-stone-500/5 hover:bg-stone-100 text-neutral-800')
                      }`}
                    >
                      <span>{room.name}</span>
                      <span className="font-mono text-[9px] opacity-80">{room.dimensions}</span>
                    </button>
                  ))}
                </div>
              </div>
            </div>
          ) : (
            <div className="flex flex-col items-center justify-center p-12 border border-dashed rounded text-stone-500">
              No rooms detected in the floor plan database.
            </div>
          )}
        </div>
      </div>
      
      {/* Key Architectural Highlights Banner */}
      {property.floorPlanKeyHighlights && property.floorPlanKeyHighlights.length > 0 && (
        <div className={`mt-8 pt-6 border-t ${isDarkCanvas ? 'border-[#4e4639]/20' : 'border-[#e8ded1]/30'}`}>
          <h5 className={`text-[10px] tracking-widest font-bold uppercase mb-3 ${isDarkCanvas ? 'text-[#ffdea5]' : 'text-[#775a19]'}`}>
            Curator's Circulation Highlights
          </h5>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
            {property.floorPlanKeyHighlights.map((highlight, idx) => (
              <div key={idx} className="flex gap-2.5 items-start">
                <span className={`w-5 h-5 rounded-full flex items-center justify-center text-[10px] font-bold ${isDarkCanvas ? 'bg-[#ffdea5]/10 text-[#ffdea5]' : 'bg-[#775a19]/10 text-[#775a19]'} shrink-0`}>
                  {idx + 1}
                </span>
                <p className={`text-[11px] leading-relaxed ${isDarkCanvas ? 'text-surface-dim' : 'text-neutral-600'}`}>
                  {highlight}
                </p>
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  );
}
