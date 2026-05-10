export const cores = {
  primaria:     "#1e1b4b",
  acento:       "#4f46e5",
  fundo:        "#f0f2f5",
  superficie:   "#ffffff",
  bordaSuave:   "#f3f4f6",
  borda:        "#d1d5db",
  textoP:       "#111827",
  textoS:       "#374151",
  textoM:       "#6b7280",
  textoD:       "#9ca3af",
  sucesso:      "#10b981",
  sucessoBg:    "#d1fae5",
  sucessoTx:    "#065f46",
  perigo:       "#ef4444",
  perigoBg:     "#fee2e2",
  perigoTx:     "#991b1b",
  aviso:        "#d97706",
  avisoBg:      "#fef3c7",
  avisoTx:      "#92400e",
};

const estilos = {
  app: {
    minHeight: "100vh",
    background: cores.fundo,
    fontFamily: "'Segoe UI', system-ui, sans-serif",
  },
  header: {
    background: cores.primaria,
    borderBottom: "1px solid #312e81",
    padding: "0 24px",
  },
  headerInner: {
    maxWidth: 1100,
    margin: "0 auto",
    height: 56,
    display: "flex",
    alignItems: "center",
    justifyContent: "space-between",
  },
  logoWrapper:     { display: "flex", alignItems: "center", gap: 10 },
  logoDot: {
    width: 10, height: 10, borderRadius: "50%",
    background: "#818cf8", boxShadow: "0 0 8px #818cf8",
  },
  logoTexto:       { color: "#e0e7ff", fontWeight: 600, fontSize: 17, letterSpacing: "0.01em" },
  headerSubtitulo: { color: cores.textoM, fontSize: 13 },
  navLink: {
    color: "#a5b4fc", fontSize: 14, fontWeight: 500,
    cursor: "pointer", padding: "4px 12px",
    borderRadius: 6, border: "1px solid transparent",
    background: "transparent", transition: "all 0.15s",
  },
  navLinkAtivo: {
    color: "#e0e7ff", background: "#312e81",
    borderColor: "#4f46e5",
  },
  main:      { maxWidth: 1100, margin: "0 auto", padding: "32px 24px" },
  barraTopo: {
    display: "flex", alignItems: "flex-start",
    justifyContent: "space-between", marginBottom: 24,
  },
  tituloSecao: { fontSize: 22, fontWeight: 700, color: cores.primaria },
  subtitulo:   { fontSize: 13, color: cores.textoM, marginTop: 2 },
  toast: {
    padding: "12px 16px", borderRadius: 8, marginBottom: 20,
    fontSize: 14, animation: "fadeIn 0.3s ease",
  },
  tabelaWrapper: {
    background: cores.superficie, borderRadius: 12,
    boxShadow: "0 1px 3px rgba(0,0,0,0.08)",
    overflow: "hidden",
  },
  tabela: { width: "100%", borderCollapse: "collapse" },
  th: {
    background: cores.primaria, color: "#c7d2fe",
    padding: "12px 16px", textAlign: "left",
    fontSize: 12, fontWeight: 600,
    letterSpacing: "0.06em", textTransform: "uppercase",
  },
  td: {
    padding: "13px 16px", fontSize: 14, color: cores.textoS,
    borderBottom: `1px solid ${cores.bordaSuave}`,
    background: cores.superficie, transition: "background 0.15s",
  },
  // Botões
  btnNovo: {
    background: cores.acento, color: "#fff",
    border: "none", borderRadius: 8,
    padding: "10px 20px", fontSize: 14, fontWeight: 600,
    cursor: "pointer", display: "flex", alignItems: "center", gap: 6,
    whiteSpace: "nowrap",
  },
  btnIcone: {
    background: "transparent", border: `1px solid ${cores.borda}`,
    borderRadius: 6, padding: "6px 8px",
    cursor: "pointer", color: "#4b5563",
    transition: "all 0.15s", marginRight: 4,
    display: "inline-flex", alignItems: "center",
  },
  btnIconePerigo:    { color: cores.perigo,  borderColor: "#fecaca" },
  btnIconeReativar:  { color: cores.sucesso, borderColor: "#6ee7b7" },
  btnIconeVizualizar:{ color: "#6366f1",     borderColor: "#c7d2fe" },
  btnPrimario: {
    background: cores.acento, color: "#fff",
    border: "none", borderRadius: 8,
    padding: "10px 22px", fontSize: 14, fontWeight: 600,
    cursor: "pointer", display: "flex", alignItems: "center", gap: 6,
  },
  btnSecundario: {
    background: cores.superficie, color: cores.textoS,
    border: `1px solid ${cores.borda}`, borderRadius: 8,
    padding: "10px 22px", fontSize: 14, fontWeight: 500,
    cursor: "pointer",
  },
  // Página de formulário (não-modal)
  paginaFormulario: {
    maxWidth: 640,
    margin: "32px auto",
    padding: "0 24px",
  },
  formularioCard: {
    background: cores.superficie,
    borderRadius: 12,
    boxShadow: "0 1px 3px rgba(0,0,0,0.08)",
    overflow: "hidden",
  },
  formularioHeader: {
    background: cores.primaria,
    padding: "20px 28px",
    display: "flex",
    alignItems: "center",
    justifyContent: "space-between",
  },
  formularioTitulo: {
    color: "#e0e7ff", fontWeight: 700, fontSize: 18,
  },
  formularioBody: {
    padding: "28px",
    display: "flex", flexDirection: "column", gap: 20,
  },
  formularioFooter: {
    padding: "16px 28px",
    borderTop: `1px solid ${cores.bordaSuave}`,
    display: "flex", justifyContent: "flex-end", gap: 10,
  },
  // Campos
  campoGrupo:    { display: "flex", flexDirection: "column", gap: 6 },
  label:         { fontSize: 13, fontWeight: 600, color: cores.textoS },
  obrig:         { color: cores.perigo },
  input: {
    border: `1px solid ${cores.borda}`, borderRadius: 8,
    padding: "10px 12px", fontSize: 14, color: cores.textoP,
    background: cores.superficie, width: "100%",
    transition: "border-color 0.15s",
  },
  inputDisabled: {
    background: "#f9fafb", color: cores.textoM, cursor: "not-allowed",
  },
  mensagemErro: {
    background: "#fef2f2", border: "1px solid #fecaca",
    borderRadius: 8, padding: "10px 14px",
    color: cores.perigoTx, fontSize: 13,
  },
  centrado: {
    display: "flex", alignItems: "center", justifyContent: "center",
    padding: 48, color: cores.textoD,
  },
  erroCard: {
    background: cores.superficie, borderRadius: 12, padding: 32,
    textAlign: "center", color: cores.textoS,
    boxShadow: "0 1px 3px rgba(0,0,0,0.08)",
  },
};

export default estilos;
