package com.memoform.game;

/**
 * Classe abstraite représentant une étape dans une chaîne de responsabilité.
 * Elle définit la structure de base pour chaque étape du jeu, en permettant
 * de chaîner des actions successives.
 * 
 * @see StepLink
 * 
 * @see MemoForm
 */
public abstract class StepLink {
    
    // Lien vers l'étape suivante de la chaîne
    private StepLink next = null;

    /**
     * Définit l'étape suivante dans la chaîne.
     *
     * @param next L'étape suivante à ajouter à la chaîne
     */
    public final void setNext(StepLink next) {
        this.next = next;
    }

    /**
     * Définit la dernière étape de la chaîne. Si l'étape suivante est déjà définie,
     * elle passe l'ajout de la dernière étape à cette dernière.
     *
     * @param last L'étape finale à ajouter à la chaîne
     */
    public final void setLast(StepLink last) {
        // Si l'étape suivante est null, on définit la dernière étape comme suivante
        if (this.next == null) this.next = last;
        else this.next.setLast(last); // Sinon, on la passe à l'étape suivante
    }

    /**
     * Action locale spécifique à chaque étape. Cette méthode doit être implémentée
     * par chaque sous-classe pour définir l'action particulière de cette étape.
     *
     * @param game L'instance du jeu qui sera modifiée par cette étape
     */
    protected abstract void localAction(MemoForm game); 

    /**
     * Exécute l'action associée à cette étape et déclenche l'action de la classe dérivée
     * en appelant la méthode localAction() puis finit par appeler la chaine suivant si présente.
     *
     * @param game L'instance du jeu pour lequel l'action sera effectuée
     */
    public final void action(MemoForm game) {
        System.out.println(this.getClass().getSimpleName());
        // Exécution de l'action locale de l'étape
        this.localAction(game);
        if (this.next != null) {
            // On continue avec l'étape suivante
            this.next.action(game);
        }
    }

}
