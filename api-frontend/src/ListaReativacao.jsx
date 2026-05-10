import estilos, { cores } from "./estilos.js";
import { IconeOlho, IconeReativar, IconeLoading } from "./Icones.jsx";

// ─── ListaReativacao ──────────────────────────────────────────────────────────
// Tela de reativação de produtos excluídos logicamente (ativo = false).
// Segue o mesmo layout da ListaProdutos, mas com os botões de ação diferentes:
//   • Olho  — abre a tela de visualização (campos bloqueados)
//   • Seta  — reativa o produto (ativo = true) e volta para a tela principal
//
// Props:
//   produtos       — array de produtos inativos
//   carregando     — boolean
//   erro           — string ou ""
//   feedback       — { tipo, msg } ou null
//   onVisualizar   — recebe o objeto Produto e abre a visualização
//   onReativar     — recebe o objeto Produto e executa a reativação
//   onRecarregar   — recarrega a lista

export default function ListaReativacao({
  produtos, carregando, erro, feedback,
  onVisualizar, onReativar, onRecarregar,
}) {
  return (
    <main style={estilos.main}>
      {feedback && (
        <div style={{
          ...estilos.toast,
          background:  feedback.tipo === "sucesso" ? cores.sucessoBg : cores.perigoBg,
          borderLeft: `4px solid ${feedback.tipo === "sucesso" ? cores.sucesso : cores.perigo}`,
          color:       feedback.tipo === "sucesso" ? cores.sucessoTx : cores.perigoTx,
        }}>
          {feedback.msg}
        </div>
      )}

      <div style={estilos.barraTopo}>
        <div>
          <h2 style={estilos.tituloSecao}>Reativação de Produtos</h2>
          <p style={estilos.subtitulo}>
            {produtos.length} produto(s) inativo(s) aguardando reativação
          </p>
        </div>
      </div>

      {carregando && (
        <div style={estilos.centrado}>
          <IconeLoading />
          <span style={{ marginLeft: 8, color: cores.textoM }}>Carregando…</span>
        </div>
      )}

      {erro && !carregando && (
        <div style={estilos.erroCard}>
          <strong>Não foi possível carregar os produtos inativos.</strong>
          <p style={{ fontSize: 13, marginTop: 4 }}>{erro}</p>
          <button onClick={onRecarregar} style={{ ...estilos.btnSecundario, marginTop: 12 }}>
            Tentar novamente
          </button>
        </div>
      )}

      {!carregando && !erro && produtos.length === 0 && (
        <div style={estilos.erroCard}>
          <p style={{ color: cores.textoM }}>Nenhum produto inativo encontrado.</p>
        </div>
      )}

      {!carregando && !erro && produtos.length > 0 && (
        <div style={estilos.tabelaWrapper}>
          <table style={estilos.tabela}>
            <thead>
              <tr>
                {["Id", "Nome", "Descrição", "Preço", "Criado em", "Ações"].map(h => (
                  <th key={h} style={estilos.th}>{h}</th>
                ))}
              </tr>
            </thead>
            <tbody>
              {produtos.map((p, i) => (
                <tr key={p.id} style={{ animation: `slideUp ${0.05 * i + 0.1}s ease both` }}>
                  <td style={{ ...estilos.td, color: cores.textoD, fontSize: 13 }}>{p.id}</td>
                  <td style={{ ...estilos.td, fontWeight: 500, color: cores.textoM }}>
                    {p.nome}
                  </td>
                  <td style={{
                    ...estilos.td, color: cores.textoD,
                    maxWidth: 200, overflow: "hidden", textOverflow: "ellipsis", whiteSpace: "nowrap",
                  }}>
                    {p.descricao || "—"}
                  </td>
                  <td style={{ ...estilos.td, fontFamily: "monospace", color: cores.textoM }}>
                    R$ {Number(p.preco).toFixed(2).replace(".", ",")}
                  </td>
                  <td style={{ ...estilos.td, color: cores.textoD, fontSize: 13 }}>
                    {p.dataCriacao ? new Date(p.dataCriacao).toLocaleString("pt-BR") : "—"}
                  </td>
                  <td style={{ ...estilos.td, whiteSpace: "nowrap" }}>
                    <button
                      onClick={() => onVisualizar(p)}
                      style={{ ...estilos.btnIcone, ...estilos.btnIconeVizualizar }}
                      title="Visualizar produto"
                    >
                      <IconeOlho />
                    </button>
                    <button
                      onClick={() => onReativar(p)}
                      style={{ ...estilos.btnIcone, ...estilos.btnIconeReativar }}
                      title="Reativar produto"
                    >
                      <IconeReativar />
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </main>
  );
}
