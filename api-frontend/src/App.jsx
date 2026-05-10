import { useState, useEffect } from "react";
import {
  listarProdutos, listarProdutosInativos,
  criarProduto, atualizarProduto, apagarProduto, reativarProduto,
} from "./api.js";
import Header            from "./Header.jsx";
import ListaProdutos     from "./ListaProdutos.jsx";
import ListaReativacao   from "./ListaReativacao.jsx";
import FormularioProduto from "./FormularioProduto.jsx";
import estilos           from "./estilos.js";

// ─── App ──────────────────────────────────────────────────────────────────────
// Componente raiz. Gerencia o estado global e a navegação entre telas.
//
// Telas possíveis (variável "tela"):
//   "lista"       — ListaProdutos: tabela com produtos ativos
//   "novo"        — FormularioProduto: criação (produto=null)
//   "edicao"      — FormularioProduto: edição (produto=objeto)
//   "reativacao"  — ListaReativacao: tabela com produtos inativos
//   "visualizacao"— FormularioProduto: visualização, todos os campos bloqueados

export default function App() {
  // ── Estado da tela de produtos ativos ─────────────────────────────────────
  const [produtos,           setProdutos]           = useState([]);
  const [carregandoLista,    setCarregandoLista]    = useState(true);
  const [erroLista,          setErroLista]          = useState("");

  // ── Estado da tela de reativação ─────────────────────────────────────────
  const [inativos,           setInativos]           = useState([]);
  const [carregandoInativos, setCarregandoInativos] = useState(false);
  const [erroInativos,       setErroInativos]       = useState("");

  // ── Estado de navegação ──────────────────────────────────────────────────
  const [tela,               setTela]               = useState("lista");
  const [produtoSelecionado, setProdutoSelecionado] = useState(null);
  const [feedback,           setFeedback]           = useState(null);

  // ── Carregamento inicial ─────────────────────────────────────────────────
  useEffect(() => {
    carregarProdutos();
  }, []);

  // ── Funções de carregamento ──────────────────────────────────────────────
  async function carregarProdutos() {
    setCarregandoLista(true);
    setErroLista("");
    try {
      const dados = await listarProdutos();
      setProdutos(dados);
    } catch (e) {
      setErroLista(e.message);
    } finally {
      setCarregandoLista(false);
    }
  }

  async function carregarInativos() {
    setCarregandoInativos(true);
    setErroInativos("");
    try {
      const dados = await listarProdutosInativos();
      setInativos(dados);
    } catch (e) {
      setErroInativos(e.message);
    } finally {
      setCarregandoInativos(false);
    }
  }

  function mostrarFeedback(tipo, msg) {
    setFeedback({ tipo, msg });
    setTimeout(() => setFeedback(null), 3500);
  }

  // ── Navegação ─────────────────────────────────────────────────────────────
  // Chamado pelos links no Header
  function handleNavegar(destino) {
    setProdutoSelecionado(null);
    setFeedback(null);
    setTela(destino);
    if (destino === "lista")      carregarProdutos();
    if (destino === "reativacao") carregarInativos();
  }

  function abrirNovo() {
    setProdutoSelecionado(null);
    setTela("novo");
  }

  function abrirEdicao(produto) {
    setProdutoSelecionado(produto);
    setTela("edicao");
  }

  function abrirVisualizacao(produto) {
    setProdutoSelecionado(produto);
    setTela("visualizacao");
  }

  function voltarParaLista() {
    setProdutoSelecionado(null);
    setTela("lista");
    carregarProdutos();
  }

  function voltarParaReativacao() {
    setProdutoSelecionado(null);
    setTela("reativacao");
  }

  // ── Ações ────────────────────────────────────────────────────────────────
  async function handleSalvar(id, payload) {
    if (id) {
      await atualizarProduto(id, payload);
      mostrarFeedback("sucesso", "Produto atualizado com sucesso!");
    } else {
      await criarProduto(payload);
      mostrarFeedback("sucesso", "Produto criado com sucesso!");
    }
    voltarParaLista();
  }

  async function handleApagar(id) {
    if (!window.confirm("Deseja excluir este produto?")) return;
    try {
      await apagarProduto(id);
      mostrarFeedback("sucesso", "Produto excluído.");
      carregarProdutos();
    } catch (e) {
      mostrarFeedback("erro", e.message);
    }
  }

  async function handleReativar(produto) {
    try {
      await reativarProduto(produto.id);
      mostrarFeedback("sucesso", `Produto "${produto.nome}" foi reativado com sucesso!`);
      // Volta para a tela principal após reativar
      setTela("lista");
      carregarProdutos();
    } catch (e) {
      mostrarFeedback("erro", e.message);
    }
  }

  // ── Render ───────────────────────────────────────────────────────────────
  return (
    <div style={estilos.app}>
      <style>{`
        @keyframes spin    { to { transform: rotate(360deg); } }
        @keyframes fadeIn  { from { opacity:0; transform:translateY(-8px) } to { opacity:1; transform:translateY(0) } }
        @keyframes slideUp { from { opacity:0; transform:translateY(20px) } to { opacity:1; transform:translateY(0) } }
        * { box-sizing: border-box; margin: 0; padding: 0; }
        button:focus-visible { outline: 2px solid #4f46e5; outline-offset: 2px; }
        input:focus, textarea:focus { outline: 2px solid #4f46e5; outline-offset: 0; }
        tr:hover td { background: #f5f7ff !important; }
        input:disabled, textarea:disabled { outline: none !important; }
      `}</style>

      <Header tela={tela} onNavegar={handleNavegar} />

      {tela === "lista" && (
        <ListaProdutos
          produtos={produtos}
          carregando={carregandoLista}
          erro={erroLista}
          feedback={feedback}
          onNovo={abrirNovo}
          onEditar={abrirEdicao}
          onApagar={handleApagar}
          onRecarregar={carregarProdutos}
        />
      )}

      {tela === "novo" && (
        <FormularioProduto
          produto={null}
          modoVisualizacao={false}
          onCancelar={voltarParaLista}
          onSalvar={handleSalvar}
        />
      )}

      {tela === "edicao" && (
        <FormularioProduto
          produto={produtoSelecionado}
          modoVisualizacao={false}
          onCancelar={voltarParaLista}
          onSalvar={handleSalvar}
        />
      )}

      {tela === "reativacao" && (
        <ListaReativacao
          produtos={inativos}
          carregando={carregandoInativos}
          erro={erroInativos}
          feedback={feedback}
          onVisualizar={abrirVisualizacao}
          onReativar={handleReativar}
          onRecarregar={carregarInativos}
        />
      )}

      {tela === "visualizacao" && (
        <FormularioProduto
          produto={produtoSelecionado}
          modoVisualizacao={true}
          onCancelar={voltarParaReativacao}
          onSalvar={null}
        />
      )}
    </div>
  );
}
