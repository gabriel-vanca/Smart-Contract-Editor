package matchers.component;


import pipe.gui.imperial.pipe.models.petrinet.Annotation;

public class HasAnnotationFields implements Has<Annotation> {
    private String text;

    private int y;

    private int width;

    private int height;

    public HasAnnotationFields(String text, int x, int y, int width, int height) {
        this.text = text;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean matches(Annotation component) {
        return component.getY() == y && component.getX() == component.getX() && component.getWidth() == width
                && component.getHeight() == height && component.getText().equals(text);
    }
}
