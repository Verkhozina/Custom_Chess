import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.io.InputStream;
import java.util.*;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
public class ConfigParser {
    static List<Piece> parseConfig(File config) throws Exception {
        InputStream stream = new FileInputStream(config);
        List<Piece> data = new ArrayList<>();
        XMLInputFactory streamFactory = XMLInputFactory.newInstance();
        XMLStreamReader reader = streamFactory.createXMLStreamReader(stream);
        String name = "";
        String shortName = "";
        List<Pair<Integer>> startsW = new ArrayList<>();
        List<Pair<Integer>> startsB = new ArrayList<>();
        List<Move> moves = new ArrayList<>();
        while (reader.hasNext()) {
            reader.next();
            switch (reader.getEventType()) {
                case XMLStreamConstants.START_ELEMENT -> {
                    switch (reader.getLocalName()) {
                        case "name" -> {
                            reader.next();
                            name = reader.getText();
                        }
                        case "shortName" -> {
                            reader.next();
                            shortName = reader.getText();
                        }
                        case "start-point" -> {
                            startsW.add(new Pair<>(Integer.parseInt(reader.getAttributeValue(0))-1,
                                    Integer.parseInt(reader.getAttributeValue(1))-1));
                            startsB.add(new Pair<>(Integer.parseInt(reader.getAttributeValue(0))-1,
                                    8-Integer.parseInt(reader.getAttributeValue(1))));
                        }
                        case "linear-move" -> {
                            Boolean jumping = false;
                            boolean allColors = false;
                            int limit = 0;
                            for (int i = 0; i < reader.getAttributeCount(); i++) {
                                if (reader.getAttributeLocalName(i).equals("jumping")) {
                                    jumping = Boolean.parseBoolean(reader.getAttributeValue(i));
                                } else if (reader.getAttributeLocalName(i).equals("allColors")) {
                                    allColors = Boolean.parseBoolean(reader.getAttributeValue(i));
                                } else if (reader.getAttributeLocalName(i).equals("limit")) {
                                    limit = Integer.parseInt(reader.getAttributeValue(i));
                                }
                            }
                            moves.add(new MoveLinear(jumping, allColors, limit));
                        }
                        case "diagonal-move" -> {
                            Boolean jumping = false;
                            int limit = 0;
                            for (int i = 0; i < reader.getAttributeCount(); i++) {
                                if (reader.getAttributeLocalName(i).equals("jumping")) {
                                    jumping = Boolean.getBoolean(reader.getAttributeValue(i));
                                } else if (reader.getAttributeLocalName(i).equals("limit")) {
                                    limit = Integer.parseInt(reader.getAttributeValue(i));
                                }
                            }
                            moves.add(new MoveDiagonal(jumping, limit));
                        }
                        case "knight-move" -> {
                            int dist1 = 0;
                            int dist2 = 0;
                            for (int i = 0; i < reader.getAttributeCount(); i++) {
                                if (reader.getAttributeLocalName(i).equals("distance1")) {
                                    dist1 = Integer.parseInt(reader.getAttributeValue(i));
                                } else if (reader.getAttributeLocalName(i).equals("distance2")) {
                                    dist2 = Integer.parseInt(reader.getAttributeValue(i));
                                }
                            }
                            moves.add(new MoveKnight(dist1, dist2));
                        }
                        case "special-move" -> {
                            for (int i = 0; i < reader.getAttributeCount(); i++) {
                                if (reader.getAttributeLocalName(i).equals("class-name")) {
                                    try {
                                        Move move = (Move)Class.forName(reader.getAttributeValue(i)).getConstructor().newInstance();
                                        moves.add(move);
                                    } catch (ClassNotFoundException | ClassCastException e) {
                                        return null;
                                    }
                                }
                            }
                        }
                        default -> {}
                    }
                }
                case XMLStreamConstants.END_ELEMENT -> {
                    if (reader.getLocalName().equals("piece")) {
                        data.add(new Piece(name, shortName, startsW, true, moves));
                        data.add(new Piece(name, shortName, startsB, false, moves));
                        startsW = new ArrayList<>();
                        startsB = new ArrayList<>();
                        moves = new ArrayList<>();
                    }
                }
                default -> {}
            }
        }
        reader.close();
        return data;
    }
}
