app = {

    isMapReady : true,

    connect : function() {

        var socket = new SockJS('/ws');
        var stompClient = Stomp.over(socket);
        stompClient.connect({}, function() {
            // pull data initially
            app.loadData();

            // set up websocket subscribers
            stompClient.subscribe('/topic/currencyAggregate', function (serverResponse) {
                var data = JSON.parse(serverResponse.body);
                data.forEach(function(d) {
                    d.tradeCount = +d.tradeCount;
                });

                if (data) {
                    app.drawPieChart(data);
                } else {
                    $('#pie').html('Did not find any data');
                }
            });
            stompClient.subscribe('/topic/countryAggregate', function (serverResponse) {
                var data = JSON.parse(serverResponse.body);
                data.forEach(function(d) {
                    d.tradeCount = +d.tradeCount;
                    d.currencyPairs.forEach(function(c) {
                        c.tradeCount = +c.tradeCount;
                    })
                });

                if (data) {
                    app.drawWorldMap(data);
                } else {
                    $('#globalMap').html('Did not find any data');
                }
            });
        });
    },

    loadData : function() {
        $.ajax({
            url: '/loadData',
            type: 'GET'
        });
    },

    drawWorldMap : function(serverData) {

        var width = 1170,
            height = 503;

        var projection = d3.geo.equirectangular()
            .scale(153)
            .translate([width / 2, height / 2])
            .precision(.1);

        var path = d3.geo.path()
            .projection(projection);

        var svg = d3.select('#globalMap').select('svg');
        if(svg.empty()) {
            svg = d3.select('#globalMap').append('svg');
            svg.attr('width', width)
                .attr('height', height)
                .attr('class', 'datamap');
        }

        d3.json("../../datamaps/world.topo.json", function(error, data) {

            // there's no need to redraw the map if it's already done
            if (!app.isMapReady) {

                var subunits = svg.select('.datamaps-subunits');
                if (subunits.empty()) {
                    subunits = app.addLayer(svg, 'datamaps-subunits', true);
                }

                var geoData = topojson.feature(data, data.objects['world']).features;
                // hide Antarctica
                geoData = geoData.filter(function (feature) {
                    return feature.id !== "ATA";
                });

                // redraw the global map
                var geo = subunits.selectAll('.datamaps-subunit').data(geoData);
                geo.enter()
                    .append('path')
                    .attr('d', path)
                    .attr('class', function (d) {
                        return 'datamaps-subunit ' + d.id;
                    })
                    .attr('data-toggle', 'tooltip')
                    .attr('title', function (d) {
                        var name = d.properties.name;
                        return name;
                    });

                app.isMapReady = true;
            }

            var bubbleG = svg.select('.bubbles');
            if (bubbleG.empty()) {
                bubbleG = app.addLayer(svg, 'bubbles', false);
            }

            // remove existing markers
            bubbleG.selectAll('.datamaps-bubble').remove();

            // add new markers as circles
            var bubbles = bubbleG.selectAll('.datamaps-bubble').data(serverData);
            bubbles.enter().append('circle')
                .attr('r', 10)
                .attr('class', 'datamaps-bubble')
                .attr('cx', function(d) {
                    var latLng;
                    latLng = projection([d.location.x, d.location.y]);
                    if (latLng) return latLng[0];
                })
                .attr('cy', function(d) {
                    var latLng;
                    latLng = projection([d.location.x, d.location.y]);
                    if (latLng) return latLng[1];
                })
                .attr('data-toggle', 'popover')
                .attr('title', 'Details')
                .attr('data-content', function(d) {
                    var html = '<div><span>Country: </span>' + d.name + '</div>';
                    html += '<div"><span>Country Code: </span>' + d.code + '</div>';
                    html += '<div><strong>Trades: </strong>' + d.tradeCount + '</div>';
                    var currencyPairs = d.currencyPairs;
                    if(currencyPairs) {
                        html += '<hr/><div><span>Top 5 Traded Currency Pairs</span></div>';
                        html += '<div class="row"><div class="col-md-5"><strong>Pair</strong></div><div class="col-md-5"><strong>Trades</strong></div></div>';
                        currencyPairs.forEach(function(currency) {
                            html += '<div class="row text-center"><div class="col-md-5">'+currency.pair+'</div><div class="col-md-5">'+currency.tradeCount+'</div></div>';
                        });
                    }
                    return html;
                });

            // initiate bootstrap tooltip and popover
            $('[data-toggle="tooltip"]').tooltip({
                container: 'body',
                placement: 'top',
                html: true,
                animation: true
            });
            $('[data-toggle="popover"]').popover({
                container: 'body',
                placement: 'top',
                html: true,
                animation: true,
                trigger: 'hover'
            });
        });
    },

    drawPieChart : function(data) {
        var width = 500,
            height = 500,
            r      = 200;

        var color = d3.scale.category20c();
        var arc = d3.svg.arc()
            .outerRadius(r - 10)
            .innerRadius(0);
        var pie = d3.layout.pie()
            .sort(null)
            .value(function(d) { return d.tradeCount; });
        var pieData = pie(data);

        var svg = d3.select('#pie').selectAll('svg').data([data]);
        svg.enter().append('svg').append('g');
        svg.attr('width', width).attr('height', height);

        // set up groups
        var g = svg.select('g')
            .attr('transform', 'translate(' + width/2 + ',' + height/2 + ')');

        // remove existing arcs in order to refresh it with new data
        g.selectAll('.arc').remove();

        var arcs = g.selectAll('.arc')
            .data(pieData);
        arcs.enter().append('g')
            .attr('class', 'arc')
            .attr('data-toggle', 'tooltip')
            .attr('title', function(d) {
                return 'Number of Trades: ' + d.data.tradeCount;
            });

        // draw pieces
        arcs.append('path')
            .attr('d', arc)
            .style('fill', function(d) { return color(d.data.pair); })

        // label pieces
        arcs.append('text')
            .attr('transform', function(d) { return 'translate(' + arc.centroid(d) + ')'; })
            .attr('dy', '.35em')
            .style('text-anchor', 'middle')
            .text(function(d) { return d.data.pair });
    },

    addLayer : function(svg, className, first) {

        var layer;
        if (first) {
            layer = svg.insert('g', ':first-child')
        } else {
            layer = svg.append('g')
        }
        return layer.attr('class', className || '');
    }
}