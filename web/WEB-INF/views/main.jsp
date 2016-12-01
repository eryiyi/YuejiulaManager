<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<style>
    .container{
        padding-top: 100px;
    }
</style>
<div class="container">
    <div class="row">
        <div class="col-md-4">
            <canvas id="chart1" height="200" width="300"></canvas>
        </div>
        <%--<div class="col-md-4">--%>
            <%--<canvas id="chart2" height="200" width="300"></canvas>--%>
        <%--</div>--%>
        <div class="col-md-4">
            <canvas id="chart3" height="200" width="300"></canvas>
        </div>
    </div>
</div>

<script>
    var ctx1 = $('#chart1');
//    var ctx2 = $('#chart2');
    var ctx3 = $('#chart3');

        new Chart(ctx1, {
            type: 'doughnut',
            data: {
                labels: ["全部会员 ${memberCount}", "关禁闭的会员 ${closeMemberCount}", "今日注册会员 ${memberCountNoDay}"],
                datasets: [
                    {
                        data: [${memberCount}, ${closeMemberCount}, ${memberCountNoDay}],
                        backgroundColor: [
                            "#FF6384",
                            "#36A2EB",
                            "#FFCE56"
                        ],
                        hoverBackgroundColor: [
                            "#FF6384",
                            "#36A2EB",
                            "#FFCE56"
                        ]
                    }]
            },
            options:{
                tooltips:{
                    enabled:false
                }
            }
        });


        <%--new Chart(ctx2, {--%>
            <%--type: 'doughnut',--%>
            <%--data: {--%>

                <%--labels: ["全部商品 ${countGoodsAll}"],--%>
                <%--datasets: [--%>
                    <%--{--%>
                        <%--data: [${countGoodsAll}],--%>
                        <%--backgroundColor: [--%>
                            <%--"#FF6384"--%>
                        <%--],--%>
                        <%--hoverBackgroundColor: [--%>
                            <%--"#FF6384"--%>
                        <%--]--%>
                    <%--}]--%>
            <%--},--%>
            <%--options:{--%>
                <%--tooltips:{--%>
                    <%--enabled:false--%>
                <%--}--%>
            <%--}--%>
        <%--});--%>

        new Chart(ctx3, {
            type: 'doughnut',
            data: {
                labels: ["全部订单 ${countOrderAll}", "已完成订单 ${countOrderDone}", "今日订单统计 ${countOrderDay}"],
                datasets: [
                    {
                        data: [${countOrderAll}, ${countOrderDone}, ${countOrderDay}],
                        backgroundColor: [
                            "#FF6384",
                            "#36A2EB",
                            "#FFCE56"
                        ],
                        hoverBackgroundColor: [
                            "#FF6384",
                            "#36A2EB",
                            "#FFCE56"
                        ]
                    }]
            },
            options:{
                tooltips:{
                    enabled:false
                }
            }
        });

</script>