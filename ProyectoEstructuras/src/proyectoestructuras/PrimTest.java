/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoestructuras;

/**
 *
 * @author calvinespinoza
 */
import java.util.ArrayList;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;

import org.graphstream.algorithm.Prim;
import org.graphstream.algorithm.generator.BarabasiAlbertGenerator;
import org.graphstream.algorithm.generator.BaseGenerator;
import org.graphstream.algorithm.generator.DorogovtsevMendesGenerator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

public class PrimTest {

    public static void main(String... args) {
        DorogovtsevMendesGenerator gen = new DorogovtsevMendesGenerator();
        //BaseGenerator gen =new BarabasiAlbertGenerator();
        Graph graph = new DefaultGraph("Prim Test");
        Graph graph2 = new DefaultGraph("Actual Test");

        String css = "edge .notintree {size:1px;fill-color:gray;text-font:montserrat;} "
                + "edge .intree {size:3px;fill-color:black;text-font:montserrat;}"
                + "node {fill-mode: dyn-plain;fill-color: red, blue;text-size:16;text-font:montserrat;"
                + "size: 20px;}";

        graph.addAttribute("ui.stylesheet", css);
        graph.display();
        graph2.addAttribute("ui.stylesheet", css);
        //graph2.display();

        gen.addEdgeLabels(true);
        gen.addEdgeAttribute("weight");
        gen.setEdgeAttributesRange(1, 100.0);
        gen.addNodeLabels(true);
        gen.addSink(graph);
        gen.begin();

        for (int i = 0; i < 6 && gen.nextEvents(); i++)
			;
        gen.end();

        for (int i = 0; i < graph.getEdgeCount(); i++) {
            Edge e = graph.getEdge(i);
            Double d = e.getAttribute("weight");
            System.out.println(e.getId() + " : " + d);
        }

        //graph.getNode(0).setAttribute("ui.color", 0.5);
        ArrayList<Node> nodes = new ArrayList();
        ArrayList<Edge> edges = new ArrayList();
        String i = "0";

        while (graph.getNodeCount() != nodes.size()) {
            Node n = graph.getNode(i);
            double low = 1000000;
            //double weight = 0;
            int value = 0;
            for (int j = 0; j < n.getEdgeSet().size(); j++) {
                if (!edges.contains(n.getEdge(j))) {
                    double weight = n.getEdge(j).getAttribute("weight");
                    if (weight < low) {
                        low = weight;
                        value = j;
                    }
                }
            }
            System.out.println(i);

            if (n.getId().equals(n.getEdge(value).getSourceNode().getId())) {//&& !nodes.contains(n.getEdge(value).getTargetNode())) {
                i = n.getEdge(value).getTargetNode().getId();
            } else if (n.getId().equals(n.getEdge(value).getTargetNode().getId())) {//&& !nodes.contains(n.getEdge(value).getSourceNode())) {
                i = n.getEdge(value).getSourceNode().getId();
            }
            if (!nodes.contains(graph.getNode(i))) {
                n.getEdge(value).changeAttribute("ui.style", "size:3px;fill-color:black;");
                nodes.add(n);

            }
            edges.add(n.getEdge(value));

            //}
        }

        System.out.println("hey");

        /*
        Prim prim = new Prim("ui.class", "intree", "notintree");

        prim.init(graph);
        prim.compute();
         */
    }

}
