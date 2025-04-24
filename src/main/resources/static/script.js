// ======= script.js =======

// DADOS SIMULADOS
let records = [
    {id: 1, nome: "Maria Silva",  email: "maria@email.com",  status: "Ativo"},
    {id: 2, nome: "João Souza",   email: "joao@email.com",   status: "Inativo"},
    {id: 3, nome: "Ana Santos",   email: "ana@email.com",    status: "Ativo"}
];
let nextId = 4;

// --- Renderiza a tabela ao abrir a página
window.onload = () => {
    renderTable();
    atualizarDataHora(); // Garante que a data e hora sejam exibidas ao carregar a página
};

// -------- CRUD PRINCIPAL ------------
function renderTable() {
    const tbody = document.getElementById("records-body");
    tbody.innerHTML = "";
    records.forEach(registro => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${registro.nome}</td>
            <td>${registro.email}</td>
            <td>
              ${registro.status === 'Ativo' ? '🟢' : '⚪'}
              ${registro.status}
            </td>
            <td>
                <button class="btn btn-edit" onclick="editRecord(${registro.id})">✏️ Editar</button>
                <button class="btn btn-delete" onclick="confirmDelete(${registro.id})">🗑️ Excluir</button>
            </td>
        `;
        tbody.appendChild(row);
    });
}

// -------- Formulário Modal (Novo/Editar) ---------
function openForm(record = null) {
    document.getElementById('form-modal').style.display = 'flex';
    document.getElementById('form-title').innerText = record ? "Editar Registro" : "Novo Registro";
    document.getElementById('record-form').reset();
    if(record) {
        document.getElementById('form-id').value       = record.id;
        document.getElementById('form-nome').value     = record.nome;
        document.getElementById('form-email').value    = record.email;
        document.getElementById('form-status').value   = record.status;
    } else {
        document.getElementById('form-id').value = "";
    }
}
function closeForm() {
    document.getElementById('form-modal').style.display = 'none';
}

function submitForm(event) {
    event.preventDefault();
    const id = document.getElementById("form-id").value;
    const nome = document.getElementById("form-nome").value.trim();
    const email = document.getElementById("form-email").value.trim();
    const status = document.getElementById("form-status").value;

    // Simulação de validação
    if(!nome || !email) {
        showModal("❌", "Preencha todos os campos!");
        return;
    }
    if (!validateEmail(email)) {
        showModal("🔴", "E-mail inválido!");
        return;
    }
    if(id) {
        // Editar
        const idx = records.findIndex(r => r.id == id);
        if(idx !== -1) {
            records[idx] = {id: Number(id), nome, email, status};
            showModal("✅", "Registro alterado com sucesso!");
        } else {
            showModal("❌", "Erro ao alterar: registro não encontrado!");
        }
    } else {
        // Adicionar
        records.push({id: nextId++, nome, email, status});
        showModal("✅", "Registro adicionado com sucesso!");
    }
    closeForm();
    renderTable();
}

// -------- EDITAR --------
function editRecord(id) {
    const record = records.find(r => r.id === id);
    if(record) openForm(record);
}

// -------- EXCLUIR --------
function confirmDelete(id) {
    const reg = records.find(r => r.id === id);
    if (!reg) return;
    if (confirm(`Tem certeza que deseja excluir “${reg.nome}”?`)) {
        records = records.filter(r => r.id !== id);
        renderTable();
        showModal("🗑️", "Registro excluído com sucesso!");
    }
}

// -------- NOTIFICAÇÃO MODAL --------
function showModal(emoji, message) {
    document.getElementById('modal-emoji').innerText = emoji;
    document.getElementById('modal-message').innerText = message;
    document.getElementById('modal-feedback').style.display = 'flex';
}
function closeModal() {
    document.getElementById('modal-feedback').style.display = 'none';
}

// --------- RELATÓRIO ---------
function showReport() {
    // Redireciona para a página do relatório
    window.location.href = "relatorios/relatorio-cliente.html";
}

// Função para atualizar a data e hora em tempo real
function atualizarDataHora() {
    const agora = new Date();
    const dataHoraFormatada = agora.toLocaleString();
    document.getElementById('data-hora').textContent = dataHoraFormatada;
}

// Atualiza a data e hora a cada segundo
setInterval(atualizarDataHora, 1000);

// --------- Utilitários ---------
function validateEmail(email) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}