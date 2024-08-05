private void initUnrevealedCardButtons(int cardAmount, boolean enable) {
    initCardButtonsActionListener();
    cardButtons = new JButton[cardAmount];
    for (int i = 0; i < cardButtons.length; i++) {
        cardButtons[i] = new JButton("" + i, unrevealed) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (!isEnabled() && getDisabledIcon() != null) {
                    getDisabledIcon().paintIcon(this, g, 0, 0);
                }
            }
        };
        cardButtons[i].setActionCommand("" + i);
        cardButtons[i].setText(null);
        cardButtons[i].setContentAreaFilled(false);
        cardButtons[i].setEnabled(enable);
        cardButtons[i].setDisabledIcon(unrevealed);
        cardButtons[i].setMargin(new Insets(0, 0, 0, 0));
        cardButtons[i].setBorderPainted(true);
        cardButtons[i].setHorizontalAlignment(SwingConstants.CENTER);
        cardButtons[i].setVerticalAlignment(SwingConstants.CENTER);
        cardButtons[i].addActionListener(cardButtonsActionListener);
    }
}

private void initCardButtonsActionListener() {
    cardButtonsActionListener = new ActionListener() {
        public void actionPerformed(ActionEvent event) {
            int buttonActionCommand = Integer.parseInt(event.getActionCommand());
            updateGameInfoLabel();
            memory.clickRegister();
            switch (memory.getClickCounter()) {
                case 1: // Primeiro clique do usuário.
                    clickedCardButton1 = buttonActionCommand;
                    setButtonIcon(cardButtons[clickedCardButton1], icons[clickedCardButton1]);
                    break;
                case 2: // Segundo clique do usuário.
                    clickedCardButton2 = buttonActionCommand;
                    if (clickedCardButton1 == clickedCardButton2) {
                        text.setText(text.getText() + "<html><br>" + getGameWarningString() + "</html>");
                        text.setForeground(Color.RED);
                        memory.clickCancel();
                        break;
                    } else {
                        setButtonIcon(cardButtons[clickedCardButton2], icons[clickedCardButton2]);
                        img1 = (ImageIcon) cardButtons[clickedCardButton1].getIcon();
                        img2 = (ImageIcon) cardButtons[clickedCardButton2].getIcon();
                        if (memory.compareCards(img1, img2)) {
                            memory.playerHit();
                            JOptionPane.showMessageDialog(null, getPlayerHitString());
                            cardButtons[clickedCardButton1].setEnabled(false);
                            cardButtons[clickedCardButton1].setDisabledIcon(img1);
                            cardButtons[clickedCardButton2].setEnabled(false);
                            cardButtons[clickedCardButton2].setDisabledIcon(img2);
                        } else {
                            memory.playerError();
                            JOptionPane.showMessageDialog(null, getPlayerErrorString());
                            for (int c = 0; c < cardAmount; c++)
                                cardButtons[c].setIcon(unrevealed);
                        }
                        break;
                    }
            }
        }
    };
}

private void setButtonIcon(JButton button, ImageIcon icon) {
    Image scaledImage = icon.getImage().getScaledInstance(button.getWidth(), button.getHeight(), Image.SCALE_SMOOTH);
    button.setIcon(new ImageIcon(scaledImage));
}
