import estilos from "./estilos.js";

// ─── Header ───────────────────────────────────────────────────────────────────
// Barra de navegação superior. Recebe a tela atual e um callback de navegação
// para permitir a troca entre a lista principal e a tela de reativação.
//
// Props:
//   tela       — string com a tela atualmente ativa ("lista" | "reativacao" | outras)
//   onNavegar  — callback chamado ao clicar nos links de navegação; recebe o nome da tela

export default function Header({ tela, onNavegar }) {
  const linkAtivo = (nome) => ({
    ...estilos.navLink,
    ...(tela === nome ? estilos.navLinkAtivo : {}),
  });

  return (
    <header style={estilos.header}>
      <div style={estilos.headerInner}>
        <div style={estilos.logoWrapper}>
          <div style={estilos.logoDot} />
          <span style={estilos.logoTexto}>API Produtos</span>
        </div>
        <nav style={{ display: "flex", alignItems: "center", gap: 4 }}>
          <button
            style={linkAtivo("lista")}
            onClick={() => onNavegar("lista")}
          >
            Produtos
          </button>
          <button
            style={linkAtivo("reativacao")}
            onClick={() => onNavegar("reativacao")}
          >
            Reativação
          </button>
        </nav>
        <span style={estilos.headerSubtitulo}>LIP1 · ITB Belval</span>
      </div>
    </header>
  );
}
