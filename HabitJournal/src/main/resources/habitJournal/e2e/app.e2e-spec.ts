import { HabitJournalPage } from './app.po';

describe('habit-journal App', () => {
  let page: HabitJournalPage;

  beforeEach(() => {
    page = new HabitJournalPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!');
  });
});
