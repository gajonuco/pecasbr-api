# 0001 - Validar a origem das notificações do webhook do Asaas

## Status
### Aceito
   
## Contexto 
<div style="text-align: justify;">
O endpoint `/webhook/asaas` recebe eventos de mudança de status de pagamento enviados pelo Asaas e atualiza o pedido correspondente para `PAGO`. Por precisar ser acessível pelo Asaas sem autenticação prévia, esse endpoint estava público (`permitAll`), sem nenhuma validação de que a requisição realmente partiu do Asaas. Qualquer requisição realmente partiu do Asaas. Qualquer requisição externa com um `paymentId` correspondente a um pedido existente conseguia marcá-lo como pago sem nenhum pagamento real ter ocorrido. 
</div>

## Decisão
<div style="text-align: justify;">
Validar o header `asaas-acces-token`, enviado pelo Asaas em toda notificação quando um token é configurado no painel do gateway, comparando-o com um valor definido via variável de ambiente (`ASAAS_WEBHOOK_TOKEN`). A comparação usa `MessageDigest.isEqual` (tempo constante) em vez de `String.equals`, evitando um canal de timing na tentativa de descobrir o token.
</div>

## Alternativas consideradas
<div style="text-align: justify;">
- *Restringir por IP de Origem: descartada - a faixa de IPs do Asaas pode mudar sem aviso e não prova autenticidade, só origem de rede.
- Assinatura HMAC do corpo: mais robusta, mas o Asaas não oferece esse mecanismo - só o token estático via header.
- Confiar na obscuridade da URL: rejeitada - URLs de webhook eventualmente vazam, e o risco aqui é financeiro, não cosmético.
</div>

## Consequências
<div style="text-align: justify;">
- Requisições sem o header correto retornam 401 sem processar o evento.
- Passa a existir uma dependência de configuração adicional: o token precisa estar igual no painel do Asaas e na variável de ambiente.
</div>



