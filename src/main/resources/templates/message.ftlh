<#import "./layout.ftlh" as main>
<#import "/spring.ftl" as spring>

<@main.layout; spring>
    <div class="text-center">
        <h2><@spring.message 'reset.title'/></h2>
        <p class="text-warning">${message}</p>
    </div>
</@main.layout>
