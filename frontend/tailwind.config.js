module.exports = {
  content: ['./src/**/*.{html,ts,scss}'],
  darkMode: 'class',
  theme: {
    extend: {
      colors: {
        brand: {
          DEFAULT: '#5B8DEF',
          accent: '#00E0B8',
          muted: '#1A1A1E'
        }
      }
    }
  },
  safelist: [
    { pattern: /(bg|text|border)-(brand|brand-accent|brand-muted)/ }
  ],
  plugins: []
};
