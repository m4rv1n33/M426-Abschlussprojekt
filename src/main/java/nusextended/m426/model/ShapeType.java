package nusextended.m426.model;

public enum ShapeType {
    TRIANGLE(3, "Triangle"),
    SQUARE(4, "Square"),
    PENTAGON(5, "Pentagon"),
    HEXAGON(6, "Hexagon"),
    HEPTAGON(7, "Heptagon"),
    OCTAGON(8, "Octagon");

    private final int vertices;
    private final String displayName;

    ShapeType(int vertices, String displayName) {
        this.vertices = vertices;
        this.displayName = displayName;
    }

    public int getVertices() {
        return vertices;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static ShapeType fromVertices(int vertexCount) {
        for (ShapeType type : ShapeType.values()) {
            if (type.vertices == vertexCount) {
                return type;
            }
        }
        return OCTAGON;
    }

    public static String getPolygonName(int vertexCount) {
        switch (vertexCount) {
            case 3: return "Triangle";
            case 4: return "Square";
            case 5: return "Pentagon";
            case 6: return "Hexagon";
            case 7: return "Heptagon";
            case 8: return "Octagon";
            case 9: return "Nonagon";
            case 10: return "Decagon";
            case 11: return "Hendecagon";
            case 12: return "Dodecagon";
            case 13: return "Tridecagon";
            case 14: return "Tetradecagon";
            case 15: return "Pentadecagon";
            case 16: return "Hexadecagon";
            case 17: return "Heptadecagon";
            case 18: return "Octadecagon";
            case 19: return "Enneadecagon";
            case 20: return "Icosagon";
            case 21: return "Henicosagon";
            case 22: return "Docosagon";
            case 23: return "Tricosagon";
            case 24: return "Tetracosagon";
            case 25: return "Pentacosagon";
            case 26: return "Hexacosagon";
            case 27: return "Heptacosagon";
            case 28: return "Octacosagon";
            case 29: return "Enneacosagon";
            case 30: return "Triacontagon";
            case 31: return "Hentriacontagon";
            case 32: return "Dotriacontagon";
            case 33: return "Tritriacontagon";
            case 34: return "Tetratriacontagon";
            case 35: return "Pentatriacontagon";
            case 36: return "Hexatriacontagon";
            case 37: return "Heptatriacontagon";
            case 38: return "Octatriacontagon";
            case 39: return "Ennetatriacontagon";
            case 40: return "Tetracontagon";
            case 41: return "Hentetracontagon";
            case 42: return "Dotetracontagon";
            case 43: return "Tritetracontagon";
            case 44: return "Tetratetracontagon";
            case 45: return "Pentatetracontagon";
            case 46: return "Hexatetracontagon";
            case 47: return "Heptatetracontagon";
            case 48: return "Octatetracontagon";
            case 49: return "Enneatetracontagon";
            case 50: return "Pentacontagon";
            case 51: return "Henpentacontagon";
            case 52: return "Dopentacontagon";
            case 53: return "Tripentacontagon";
            case 54: return "Tetrapentacontagon";
            case 55: return "Pentapentacontagon";
            case 56: return "Hexapentacontagon";
            case 57: return "Heptapentacontagon";
            case 58: return "Octapentacontagon";
            case 59: return "Enneapentacontagon";
            case 60: return "Hexacontagon";
            case 61: return "Henhexacontagon";
            case 62: return "Dohexacontagon";
            case 63: return "Trihexacontagon";
            case 64: return "Tetrahexacontagon";
            case 65: return "Pentahexacontagon";
            case 66: return "Hexahexacontagon";
            case 67: return "Heptahexacontagon";
            case 68: return "Octahexacontagon";
            case 69: return "Enneahexacontagon";
            case 70: return "Heptacontagon";
            case 71: return "Henheptacontagon";
            case 72: return "Doheptacontagon";
            case 73: return "Triheptacontagon";
            case 74: return "Tetraheptacontagon";
            case 75: return "Pentaheptacontagon";
            case 76: return "Hexaheptacontagon";
            case 77: return "Heptaheptacontagon";
            case 78: return "Octaheptacontagon";
            case 79: return "Enneaheptacontagon";
            case 80: return "Octacontagon";
            case 81: return "Henoctacontagon";
            case 82: return "Dooctacontagon";
            case 83: return "Trioctacontagon";
            case 84: return "Tetraoctacontagon";
            case 85: return "Pentaoctacontagon";
            case 86: return "Hexaoctacontagon";
            case 87: return "Heptaoctacontagon";
            case 88: return "Octaoctacontagon";
            case 89: return "Enneaoctacontagon";
            case 90: return "Enneacontagon";
            case 91: return "Henenneacontagon";
            case 92: return "Doenneacontagon";
            case 93: return "Trienneacontagon";
            case 94: return "Tetraenneacontagon";
            case 95: return "Pentaenneacontagon";
            case 96: return "Hexaenneacontagon";
            case 97: return "Heptaenneacontagon";
            case 98: return "Octaenneacontagon";
            case 99: return "Enneaenneacontagon";
            case 100: return "Hectagon";
            default:
                if (vertexCount < 3) return "Point";
                return vertexCount + "-gon";
        }
    }
}
