// ─── Configuração base da API ─────────────────────────────────────────────────
const API_BASE = "http://localhost:8080/produtos";

// GET /produtos — retorna apenas produtos com ativo = true
export async function listarProdutos() {
  const res = await fetch(API_BASE);
  if (!res.ok) throw new Error("Erro ao listar produtos");
  return res.json();
}

// GET /produtos/inativos — retorna produtos com ativo = false
export async function listarProdutosInativos() {
  const res = await fetch(`${API_BASE}/inativos`);
  if (!res.ok) throw new Error("Erro ao listar produtos inativos");
  return res.json();
}

// POST /produtos — cria um novo produto
export async function criarProduto(produto) {
  const res = await fetch(API_BASE, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(produto),
  });
  if (!res.ok) throw new Error("Erro ao criar produto");
  return res.json();
}

// PUT /produtos/{id} — atualiza um produto existente
export async function atualizarProduto(id, produto) {
  const res = await fetch(`${API_BASE}/${id}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(produto),
  });
  if (!res.ok) throw new Error("Erro ao atualizar produto");
  return res; //res.json();
}

// DELETE /produtos/{id} — exclusão lógica (ativo = false no back-end)
export async function apagarProduto(id) {
  const res = await fetch(`${API_BASE}/${id}`, { method: "DELETE" });
  if (!res.ok && res.status !== 204) throw new Error("Erro ao apagar produto");
}

// PATCH /produtos/{id}/reativar — define ativo = true no back-end
export async function reativarProduto(id) {
  const res = await fetch(`${API_BASE}/${id}/reativar`, { method: "PATCH" });
  if (!res.ok) throw new Error("Erro ao reativar produto");
  return res.json();
}
