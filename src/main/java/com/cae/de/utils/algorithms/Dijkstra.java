package com.cae.de.utils.algorithms;


import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class Dijkstra which implements a generic Dijkstra algorithm.
 *
 * @param <T> the node value
 */
public class Dijkstra<T> {

  /**
   * Calculates the shortest path for each node from the source.
   *
   * @param graph  the graph
   * @param source the source node
   * @param <T>    the node value
   * @return the evaluated graph
   */
  public static <T> Graph<T> calculateShortestPathFromSource(Graph<T> graph, Node<T> source) {
    source.setDistance(0);
    var settledNodes = new HashSet<Node<T>>();
    var unsettledNodes = new HashSet<Node<T>>();
    unsettledNodes.add(source);

    while (unsettledNodes.size() != 0) {
      var currentNode = getLowestDistanceNode(unsettledNodes);
      unsettledNodes.remove(currentNode);
      for (var entry : currentNode.getAdjacentNodes().entrySet()) {
        var adjacentNode = entry.getKey();
        var value = entry.getValue();
        if (!settledNodes.contains(adjacentNode)) {
          CalculateMinimumDistance(adjacentNode, value, currentNode);
          unsettledNodes.add(adjacentNode);
        }
      }
      settledNodes.add(currentNode);
    }
    return graph;
  }

  /**
   * Calculates the minimum distance.
   *
   * @param evaluationNode the node to be evaluated
   * @param edgeWeigh      the weight of the edge
   * @param sourceNode     the source node
   * @param <T>            the node value
   */
  private static <T> void CalculateMinimumDistance(Node<T> evaluationNode, Integer edgeWeigh,
      Node<T> sourceNode) {
    var sourceDistance = sourceNode.getDistance();
    if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
      evaluationNode.setDistance(sourceDistance + edgeWeigh);
      var shortestPath = new LinkedList<>(sourceNode.getShortestPath());
      shortestPath.add(sourceNode);
      evaluationNode.setShortestPath(shortestPath);
    }
  }

  /**
   * Method for computing the node with the lowest distance.
   *
   * @param unsettledNodes the unsettled nodes
   * @param <T>            the node value
   * @return the node with the lowest distance
   */
  private static <T> Node<T> getLowestDistanceNode(Set<Node<T>> unsettledNodes) {
    Node<T> lowestDistanceNode = null;
    var lowestDistance = Integer.MAX_VALUE;
    for (var node : unsettledNodes) {
      var nodeDistance = node.getDistance();
      if (nodeDistance < lowestDistance) {
        lowestDistance = nodeDistance;
        lowestDistanceNode = node;
      }
    }
    return lowestDistanceNode;
  }

  /**
   * Class Graph for holding all nodes.
   *
   * @param <T> the node value
   */
  public static class Graph<T> {

    private Set<Node<T>> nodes = new HashSet<>();
    private Node<T> startNode;
    private Node<T> endNode;

    /**
     * Getter for the start node.
     *
     * @return the start node
     */
    public Node<T> getStartNode() {
      return this.startNode;
    }

    /**
     * Setter for the start node.
     *
     * @param startNode the start node
     */
    public void setStartNode(Node<T> startNode) {
      this.startNode = startNode;
    }

    /**
     * Getter for the end node.
     *
     * @return the end node
     */
    public Node<T> getEndNode() {
      return this.endNode;
    }

    /**
     * Setter for the end node.
     *
     * @param endNode the end node
     */
    public void setEndNode(Node<T> endNode) {
      this.endNode = endNode;
    }

    /**
     * Adds a node to the graph.
     *
     * @param node the node to be added
     */
    public void addNode(Node<T> node) {
      this.nodes.add(node);
    }

    /**
     * Getter for the nodes.
     *
     * @return the nodes
     */
    public Set<Node<T>> getNodes() {
      return this.nodes;
    }

    /**
     * Setter for all nodes.
     *
     * @param nodes the set of nodes
     */
    public void setNodes(Set<Node<T>> nodes) {
      this.nodes = nodes;
    }

    /**
     * Adds multiple nodes.
     *
     * @param nodes the nodes to be added
     */
    public void addNodes(Collection<Node<T>> nodes) {
      this.nodes.addAll(nodes);
    }

    @Override
    public String toString() {
      return "Graph{" + "nodes=" + this.nodes + '}';
    }
  }

  /**
   * Class node for storing a node.
   *
   * @param <T> the node value
   */
  public static class Node<T> {

    private T value;

    private LinkedList<Node<T>> shortestPath = new LinkedList<>();

    private Integer distance = Integer.MAX_VALUE;

    private Map<Node<T>, Integer> adjacentNodes = new HashMap<>();

    /**
     * Constructor which sets the node value.
     *
     * @param value the node value
     */
    public Node(T value) {
      this.value = value;
    }

    /**
     * Adds a destination to the node.
     *
     * @param destination the node destination
     * @param distance    the distance
     */
    public void addDestination(Node<T> destination, int distance) {
      this.adjacentNodes.put(destination, distance);
    }

    /**
     * Getter for the node value.
     *
     * @return the node value
     */
    public T getValue() {
      return this.value;
    }

    /**
     * Setter for the node value.
     *
     * @param value the node value
     */
    public void setValue(T value) {
      this.value = value;
    }

    /**
     * Getter for all adjacent nodes.
     *
     * @return all adjacent nodes
     */
    public Map<Node<T>, Integer> getAdjacentNodes() {
      return this.adjacentNodes;
    }

    /**
     * Setter for all adjacent nodes.
     *
     * @param adjacentNodes the adjacent nodes
     */
    public void setAdjacentNodes(Map<Node<T>, Integer> adjacentNodes) {
      this.adjacentNodes = adjacentNodes;
    }

    /**
     * Getter for the distance
     *
     * @return the distance
     */
    public Integer getDistance() {
      return this.distance;
    }

    /**
     * Setter for the distance
     *
     * @param distance the distance
     */
    public void setDistance(Integer distance) {
      this.distance = distance;
    }

    /**
     * Getter for the shortest path.
     *
     * @return the list of nodes of the shortest path
     */
    public List<Node<T>> getShortestPath() {
      return this.shortestPath;
    }

    /**
     * Setter for the shortest path.
     *
     * @param shortestPath the list of nodes of the shortest path
     */
    public void setShortestPath(LinkedList<Node<T>> shortestPath) {
      this.shortestPath = shortestPath;
    }

    @Override
    public String toString() {
      return this.value.toString();
    }
  }
}
