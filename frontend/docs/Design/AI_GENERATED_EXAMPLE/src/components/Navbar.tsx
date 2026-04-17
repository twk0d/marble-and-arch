import { Compass, Sparkles, BookOpen, CalendarRange, Eye, EyeOff } from 'lucide-react';

interface NavbarProps {
  activeTab: 'portfolio' | 'concierge' | 'moodboard' | 'bookings';
  setActiveTab: (tab: 'portfolio' | 'concierge' | 'moodboard' | 'bookings') => void;
  isDarkCanvas: boolean;
  setIsDarkCanvas: (dark: boolean) => void;
  bookingCount: number;
}

export default function Navbar({
  activeTab,
  setActiveTab,
  isDarkCanvas,
  setIsDarkCanvas,
  bookingCount
}: NavbarProps) {
  const navItems: Array<{
    id: 'portfolio' | 'concierge' | 'moodboard' | 'bookings';
    label: string;
    icon: any;
    badge?: number;
  }> = [
    { id: 'portfolio', label: 'Portfolio', icon: Compass },
    { id: 'concierge', label: 'AI Studio Concierge', icon: Sparkles },
    { id: 'moodboard', label: 'Designer Moodboard', icon: BookOpen },
    { id: 'bookings', label: 'Private Viewings', icon: CalendarRange, badge: bookingCount },
  ];

  return (
    <nav className={`fixed top-0 left-0 right-0 z-50 ${isDarkCanvas ? 'bg-[#1c1b1b]/80 border-[#4e4639]/30 text-[#fcf9f8]' : 'bg-[#fcf9f8]/85 border-[#e8ded1]/50 text-[#1c1b1b]'} backdrop-blur-md transition-all duration-500 border-b p-4 px-6 md:px-12 flex justify-between items-center`}>
      <div className="flex items-center gap-3">
        <div className={`w-8 h-8 rounded-sm ${isDarkCanvas ? 'border-[#ffdea5] text-[#ffdea5]' : 'border-[#775a19] text-[#775a19]'} border-2 flex items-center justify-center font-serif text-sm font-bold tracking-widest`}>
          M
        </div>
        <div>
          <h1 className="font-serif text-lg font-bold tracking-[0.22em] leading-none uppercase">
            Marble &amp; Arch
          </h1>
          <p className={`font-label-sm ${isDarkCanvas ? 'text-[#ffdea5]' : 'text-[#775a19]'} text-[8px] tracking-[0.4em] uppercase mt-0.5`}>
            ESTATE STUDIO
          </p>
        </div>
      </div>

      <div className="hidden lg:flex items-center gap-8">
        {navItems.map((item) => {
          const IconComp = item.icon;
          const isActive = activeTab === item.id;
          return (
            <button
              key={item.id}
              id={`nav-tab-${item.id}`}
              onClick={() => setActiveTab(item.id)}
              className={`relative flex items-center gap-2 py-2 text-xs uppercase tracking-widest transition-all duration-300 font-medium cursor-pointer ${
                isActive 
                  ? (isDarkCanvas ? 'text-[#ffdea5]' : 'text-[#775a19]') 
                  : (isDarkCanvas ? 'text-surface-dim hover:text-white' : 'text-secondary-sand hover:text-[#1c1b1b]')
              }`}
            >
              <IconComp strokeWidth={1.5} className="w-3.5 h-3.5" />
              <span>{item.label}</span>
              
              {item.badge !== undefined && item.badge > 0 && (
                <span className={`absolute -top-1 -right-4 flex h-4 min-w-4 items-center justify-center rounded-full ${isDarkCanvas ? 'bg-[#ffdea5] text-black' : 'bg-[#775a19] text-white'} px-1 text-[8px] font-bold`}>
                  {item.badge}
                </span>
              )}
              
              {isActive && (
                <span className={`absolute bottom-0 left-0 right-0 h-0.5 ${isDarkCanvas ? 'bg-[#ffdea5]' : 'bg-[#775a19]'} rounded-full`} />
              )}
            </button>
          );
        })}
      </div>

      <div className="flex items-center gap-4">
        {/* Mobile menu - smaller indicators */}
        <div className="lg:hidden flex items-center gap-3">
          {navItems.map((item) => {
            const IconComp = item.icon;
            const isActive = activeTab === item.id;
            return (
              <button
                key={item.id}
                onClick={() => setActiveTab(item.id)}
                className={`p-2 rounded-md relative cursor-pointer ${
                  isActive 
                    ? (isDarkCanvas ? 'bg-[#ffdea5]/10 text-[#ffdea5]' : 'bg-[#775a19]/10 text-[#775a19]')
                    : 'text-neutral-500'
                }`}
                title={item.label}
              >
                <IconComp strokeWidth={1.5} className="w-4 h-4" />
                {item.badge !== undefined && item.badge > 0 && (
                  <span className={`absolute -top-0.5 -right-0.5 flex h-3.5 w-3.5 items-center justify-center rounded-full ${isDarkCanvas ? 'bg-[#ffdea5] text-black' : 'bg-[#775a19] text-white'} text-[7px] font-bold`}>
                    {item.badge}
                  </span>
                )}
              </button>
            );
          })}
        </div>

        <div className={`h-6 w-[1px] ${isDarkCanvas ? 'bg-[#4e4639]/30' : 'bg-secondary-container'} hidden md:block`} />

        {/* Dark Mode toggle */}
        <button
          onClick={() => setIsDarkCanvas(!isDarkCanvas)}
          className={`group flex items-center gap-2 p-2 rounded-full border transition-all duration-500 cursor-pointer ${
            isDarkCanvas 
              ? 'bg-[#1c1b1b] border-[#4e4639]/50 text-[#ffdea5] hover:border-[#ffdea5]' 
              : 'bg-[#fcf9f8] border-secondary-container text-secondary-oncontainer hover:border-[#775a19]'
          }`}
          title={isDarkCanvas ? "Switch to Pure Sand Light Mode" : "Switch to Deep Obsidian Canvas"}
        >
          {isDarkCanvas ? (
            <Eye strokeWidth={1.5} className="w-4 h-4 transition-transform group-hover:scale-110" />
          ) : (
            <EyeOff strokeWidth={1.5} className="w-4 h-4 transition-transform group-hover:scale-110" />
          )}
          <span className="text-[9px] tracking-widest uppercase font-semibold hidden md:inline pr-1">
            {isDarkCanvas ? "Velvet Mode" : "Stone Mode"}
          </span>
        </button>
      </div>
    </nav>
  );
}
