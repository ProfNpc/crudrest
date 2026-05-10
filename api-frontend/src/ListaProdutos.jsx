import estilos, { cores } from "./estilos.js";
import { IconeLapis, IconeX, IconeLoading } from "./Icones.jsx";

// ─── ListaProdutos ────────────────────────────────────────────────────────────
// Tela principal. Exibe a tabela de produtos ativos com botões de edição e exclusão.
// O campo "ativo" não é exibido — é usado apenas internamente para exclusão lógica.
//
// Props:
//   produtos     — array de objetos Produto (somente ativos)
//   carregando   — boolean
//   erro         — string ou ""
//   feedback     — { tipo, msg } ou null
//   onNovo       — abre o formulário de criação
//   onEditar     — recebe o objeto Produto e abre o formulário de edição
//   onApagar     — recebe o id e executa a exclusão lógica
//   onRecarregar — recarrega a lista

export default function ListaProdutos({
  produtos, carregando, erro, feedback,
  onNovo, onEditar, onApagar, onRecarregar,
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
          <h2 style={estilos.tituloSecao}>Produtos</h2>
          <p style={estilos.subtitulo}>{produtos.length} produto(s) ativo(s)</p>
        </div>
        <button onClick={onNovo} style={estilos.btnNovo}>+ Novo</button>
      </div>

      {carregando && (
        <div style={estilos.centrado}>
          <IconeLoading />
          <span style={{ marginLeft: 8, color: cores.textoM }}>Carregando…</span>
        </div>
      )}

      {erro && !carregando && (
        <div style={estilos.erroCard}>
          <strong>Não foi possível carregar os produtos.</strong>
          <p style={{ fontSize: 13, marginTop: 4 }}>{erro}</p>
          <button onClick={onRecarregar} style={{ ...estilos.btnSecundario, marginTop: 12 }}>
            Tentar novamente
          </button>
        </div>
      )}

      {!carregando && !erro && produtos.length === 0 && (
        <div style={estilos.erroCard}>
          <p style={{ color: cores.textoM }}>Nenhum produto ativo cadastrado.</p>
          <button onClick={onNovo} style={{ ...estilos.btnNovo, marginTop: 12 }}>
            + Cadastrar primeiro produto
          </button>
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
                  <td style={{ ...estilos.td, fontWeight: 500 }}>{p.nome}</td>
                  <td style={{
                    ...estilos.td, color: cores.textoM,
                    maxWidth: 200, overflow: "hidden", textOverflow: "ellipsis", whiteSpace: "nowrap",
                  }}>
                    {p.descricao || "—"}
                  </td>
                  <td style={{ ...estilos.td, fontFamily: "monospace" }}>
                    R$ {Number(p.preco).toFixed(2).replace(".", ",")}
                  </td>
                  <td style={{ ...estilos.td, color: cores.textoD, fontSize: 13 }}>
                    {p.dataCriacao ? new Date(p.dataCriacao).toLocaleString("pt-BR") : "—"}
                  </td>
                  <td style={{ ...estilos.td, whiteSpace: "nowrap" }}>
                    <button onClick={() => onEditar(p)} style={estilos.btnIcone} title="Editar">
                      <IconeLapis />
                    </button>
                    <button
                      onClick={() => onApagar(p.id)}
                      style={{ ...estilos.btnIcone, ...estilos.btnIconePerigo }}
                      title="Excluir"
                    >
                      <IconeX />
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
