const apiBase = '/food';

async function fetchJson(url, options = {}){
  const res = await fetch(url, {...{headers: {'Content-Type': 'application/json'}}, ...options});
  if (!res.ok) throw new Error(`Request failed: ${res.status}`);
  if (res.status === 204) return null;
  return res.json();
}

async function listItems(){
  try{
    const items = await fetchJson(apiBase);
    const container = document.getElementById('items-list');
    container.innerHTML = '';
    if (!items || items.length === 0){
      container.innerHTML = '<p class="empty">Nenhum item cadastrado.</p>';
      return;
    }

    for (const it of items){
      const card = document.createElement('div');
      card.className = 'card';
      const exp = it.expirationDate ? new Date(it.expirationDate).toLocaleDateString() : '';
      card.innerHTML = `
        <div class="card-body">
          <h4 class="card-title">${escapeHtml(it.name ?? '')}</h4>
          <div class="card-category">Categoria: <strong>${escapeHtml(it.category ?? '')}</strong></div>
          <div class="card-quant">Quantidade: <strong>${it.quantity ?? 0}</strong></div>
          <div class="card-valid">Validade: <strong>${exp}</strong></div>
        </div>
        <div class="card-actions">
          <button data-id="${it.id}" class="edit">Editar</button>
          <button data-id="${it.id}" class="delete">Excluir</button>
        </div>
      `;
      container.appendChild(card);
    }
  }catch(err){
    console.error(err);
    alert('Erro ao carregar items: ' + err.message);
  }
}

function escapeHtml(s){
  const map = {'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'};
  return String(s).replaceAll(/[&<>"']/g, c => map[c]);
}

function formToObject(){
  const id = document.getElementById('item-id').value || null;
  return {
    id: id ? Number(id) : undefined,
    name: document.getElementById('name').value,
    category: document.getElementById('category').value,
    quantity: Number(document.getElementById('quantity').value) || 0,
    expirationDate: document.getElementById('expirationDate').value || null
  };
}

function populateForm(it){
  document.getElementById('item-id').value = it.id ?? '';
  document.getElementById('name').value = it.name ?? '';
  document.getElementById('category').value = it.category ?? '';
  document.getElementById('quantity').value = it.quantity ?? 1;
  document.getElementById('expirationDate').value = it.expirationDate ?? '';
  document.getElementById('cancel-btn').style.display = 'inline-block';
}

function clearForm(){
  document.getElementById('item-id').value = '';
  document.getElementById('name').value = '';
  document.getElementById('category').value = '';
  document.getElementById('quantity').value = 1;
  document.getElementById('expirationDate').value = '';
  document.getElementById('cancel-btn').style.display = 'none';
}

async function submitForm(e){
  e.preventDefault();
  const obj = formToObject();
  try{
    if (obj.id){
      // update
      await fetchJson(`${apiBase}/${obj.id}`, {method: 'PUT', body: JSON.stringify(obj)});
      alert('Atualizado com sucesso');
    } else {
      await fetchJson(apiBase, {method: 'POST', body: JSON.stringify(obj)});
      alert('Criado com sucesso');
    }
    clearForm();
    listItems();
  }catch(err){
    console.error(err);
    alert('Erro ao salvar: ' + err.message);
  }
}

async function deleteItem(id){
  if (!confirm('Confirmar exclusão?')) return;
  try{
    await fetchJson(`${apiBase}/${id}`, {method: 'DELETE'});
    listItems();
  }catch(err){
    console.error(err);
    alert('Erro ao excluir: ' + err.message);
  }
}

// eventos
document.addEventListener('DOMContentLoaded', ()=>{
  listItems();
  const form = document.getElementById('item-form');
  form.addEventListener('submit', submitForm);
  document.getElementById('cancel-btn').addEventListener('click', clearForm);

  const container = document.getElementById('items-list');
  container.addEventListener('click', (e)=>{
    if (e.target.matches('button.edit')){
      const id = e.target.dataset.id;
      fetchJson(`${apiBase}/${id}`).then(populateForm).catch(err=>alert('Erro: '+err.message));
    }
    if (e.target.matches('button.delete')){
      const id = e.target.dataset.id;
      deleteItem(id);
    }
  });
});
