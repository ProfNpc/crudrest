import { useState } from "react";
import estilos from "./estilos.js";
import { IconeLoading } from "./Icones.jsx";

// ─── FormularioProduto ────────────────────────────────────────────────────────
// Tela de página inteira para criação, edição e visualização de Produto.
// Não é um modal — ocupa o mesmo espaço que as outras telas.
// O campo "ativo" não é exibido em nenhum dos modos.
//
// Props:
//   produto       — objeto Produto (edição/visualização) ou null (criação)
//   modoVisualizacao — boolean; quando true todos os campos ficam bloqueados
//   onCancelar    — volta para a tela anterior
//   onSalvar      — recebe (id, payload); null em modo visualização

export default function FormularioProduto({
  produto,
  modoVisualizacao = false,
  onCancelar,
  onSalvar,
}) {
  const isNovo = !produto;

  // Determina o título da tela conforme o modo
  function titulo() {
    if (modoVisualizacao) return "Visualizar Produto";
    if (isNovo)           return "Novo Produto";
    return                       "Editar Produto";
  }

  const [form, setForm] = useState({
    nome:      produto?.nome      ?? "",
    descricao: produto?.descricao ?? "",
    preco:     produto?.preco     ?? "",
  });
  const [salvando, setSalvando] = useState(false);
  const [erro,     setErro]     = useState("");

  function handleChange(campo, valor) {
    setForm(prev => ({ ...prev, [campo]: valor }));
    setErro("");
  }

  async function handleSalvar() {
    if (!form.nome.trim()) {
      setErro("O campo Nome é obrigatório.");
      return;
    }
    if (!form.preco || isNaN(Number(form.preco))) {
      setErro("O campo Preço deve ser um número válido.");
      return;
    }
    setSalvando(true);
    try {
      const payload = {
        nome:      form.nome.trim(),
        descricao: form.descricao.trim(),
        preco:     parseFloat(form.preco),
      };
      await onSalvar(isNovo ? null : produto.id, payload);
    } catch (e) {
      setErro(e.message);
    } finally {
      setSalvando(false);
    }
  }

  // Todos os campos ficam bloqueados quando em modo visualização
  const bloqueado = modoVisualizacao;
  const estiloInput = bloqueado
    ? { ...estilos.input, ...estilos.inputDisabled }
    : estilos.input;

  return (
    <div style={estilos.paginaFormulario}>
      <div style={estilos.formularioCard}>

        {/* Cabeçalho */}
        <div style={estilos.formularioHeader}>
          <span style={estilos.formularioTitulo}>{titulo()}</span>
        </div>

        {/* Campos */}
        <div style={estilos.formularioBody}>

          {/* Id — visível apenas em edição e visualização, sempre bloqueado */}
          {!isNovo && (
            <div style={estilos.campoGrupo}>
              <label style={estilos.label}>Id</label>
              <input
                value={produto.id}
                disabled
                style={{ ...estilos.input, ...estilos.inputDisabled }}
              />
            </div>
          )}

          {/* Nome */}
          <div style={estilos.campoGrupo}>
            <label style={estilos.label}>
              Nome {!bloqueado && <span style={estilos.obrig}>*</span>}
            </label>
            <input
              value={form.nome}
              onChange={e => handleChange("nome", e.target.value)}
              placeholder="Nome do produto"
              disabled={bloqueado}
              style={estiloInput}
            />
          </div>

          {/* Descrição */}
          <div style={estilos.campoGrupo}>
            <label style={estilos.label}>Descrição</label>
            <textarea
              value={form.descricao}
              onChange={e => handleChange("descricao", e.target.value)}
              placeholder="Descrição do produto"
              rows={4}
              disabled={bloqueado}
              style={{ ...estiloInput, resize: bloqueado ? "none" : "vertical", fontFamily: "inherit" }}
            />
          </div>

          {/* Preço */}
          <div style={estilos.campoGrupo}>
            <label style={estilos.label}>
              Preço {!bloqueado && <span style={estilos.obrig}>*</span>}
            </label>
            <input
              value={form.preco}
              onChange={e => handleChange("preco", e.target.value)}
              placeholder="Ex: 19.90"
              type="number"
              step="0.01"
              min="0"
              disabled={bloqueado}
              style={estiloInput}
            />
          </div>

          {/* Data Criação — visível em edição e visualização, sempre bloqueado */}
          {!isNovo && (
            <div style={estilos.campoGrupo}>
              <label style={estilos.label}>Data Criação</label>
              <input
                value={
                  produto.dataCriacao
                    ? new Date(produto.dataCriacao).toLocaleString("pt-BR")
                    : "—"
                }
                disabled
                style={{ ...estilos.input, ...estilos.inputDisabled }}
              />
            </div>
          )}

          {erro && <div style={estilos.mensagemErro}>{erro}</div>}
        </div>

        {/* Rodapé */}
        <div style={estilos.formularioFooter}>
          <button onClick={onCancelar} style={estilos.btnSecundario} disabled={salvando}>
            {modoVisualizacao ? "Voltar" : "Cancelar"}
          </button>
          {/* Botão Salvar só aparece nos modos de criação e edição */}
          {!modoVisualizacao && (
            <button onClick={handleSalvar} style={estilos.btnPrimario} disabled={salvando}>
              {salvando ? <><IconeLoading /> Salvando…</> : "Salvar"}
            </button>
          )}
        </div>
      </div>
    </div>
  );
}
